package com.example.work_space.config;

import com.example.work_space.filter.AuthFilter;
import com.example.work_space.filter.RoleFilter;
import com.example.work_space.interceptor.*;
import com.example.work_space.member.type.Role;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String[] AUTH_REQUIRED_PATH_PATTERNS = {
            "/members/**", "/workspaces/*", "/workspace-members/**"
    };

    private static final String[] USER_PATH = {"/members/**", "/workspaces/**", "/workspace-members/**"};
    private static final String[] ADMIN_PATHS = {"/workspaces/**"};
    private static final String[] INVITE_PATHS = {"/workspace-members/invite"};
    private static final String[] AUTH_PATHS = {"/auth/*", "/members/register"};


    private final AdminInterceptor adminInterceptor;
    private final UserInterceptor userInterceptor;
    private final AuthInterceptor authInterceptor;

    public WebConfig(AdminInterceptor adminInterceptor, UserInterceptor userInterceptor, AuthInterceptor authInterceptor) {
        this.adminInterceptor = adminInterceptor;
        this.userInterceptor = userInterceptor;
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(userInterceptor)
                .excludePathPatterns(AUTH_PATHS)
                .addPathPatterns(USER_PATH)
                .order(Ordered.HIGHEST_PRECEDENCE + 2);

        registry.addInterceptor(authInterceptor)
                .excludePathPatterns(AUTH_PATHS)
                .addPathPatterns(USER_PATH)
                .order(Ordered.HIGHEST_PRECEDENCE);

        registry.addInterceptor(adminInterceptor)
                .addPathPatterns(ADMIN_PATHS)
                .order(Ordered.HIGHEST_PRECEDENCE + 1);
    }

    @Bean
    public FilterRegistrationBean authFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthFilter());
        registrationBean.addUrlPatterns(AUTH_REQUIRED_PATH_PATTERNS);
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean adminFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RoleFilter(Role.ADMIN));
        registrationBean.addUrlPatterns(ADMIN_PATHS);
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean userFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RoleFilter(Role.USER));
        registrationBean.addUrlPatterns(USER_PATH);
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 2);
        return registrationBean;
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }
}
