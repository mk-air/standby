package com.example.work_space.card.service;

import com.example.work_space.card.dto.CardDetailResponseDto;
import com.example.work_space.card.dto.CardRequestDto;
import com.example.work_space.card.dto.CardResponseDto;
import com.example.work_space.card.dto.CardSearchRequestDto;
import com.example.work_space.card.entity.Card;
import com.example.work_space.card.repository.CardRepository;
import com.example.work_space.member.entity.Member;
import com.example.work_space.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    private final MemberRepository memberRepository;
    private final CardRepository cardRepository;

    public CardServiceImpl(MemberRepository memberRepository, CardRepository cardRepository) {
        this.memberRepository = memberRepository;
        this.cardRepository = cardRepository;
    }


    @Transactional
    @Override
    public CardResponseDto createCard(CardRequestDto requestDto) {
        // 사용자 확인
        Member member = memberRepository.findByEmail(requestDto.getMember())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자입니다."));

        Card card = Card.builder()
                .title(requestDto.getTitle())
                .contents(requestDto.getContents())
                .member(member)
                .build();

        Card savedCard = cardRepository.save(card);

        return new CardResponseDto(savedCard);
    }

    @Override
    public CardResponseDto updateCard(CardRequestDto requestDto, Long cardId) {
        // 사용자 확인
        Member member = memberRepository.findByEmail(requestDto.getMember())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자입니다."));

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 카드입니다"));

        Date inputDate = checkDate(requestDto.getDeadline());

        card.update(requestDto.getTitle(), requestDto.getContents(), inputDate, member);


        return new CardResponseDto(card);
    }

    @Override
    public CardDetailResponseDto getCardDetail(Long cardId) {

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 카드입니다"));

        return new CardDetailResponseDto(card);
    }

    @Override
    public void deleteCard(Long cardId) {

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 카드입니다"));

        cardRepository.delete(card);
    }




    public static Date checkDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false); // 엄격한 날짜 검증

        try {
            // 입력된 문자열을 Date 객체로 변환
            Date inputDate = sdf.parse(dateString);

            // 오늘 날짜를 가져오기 (시간 정보를 제거한 오늘 날짜)
            Date today = sdf.parse(sdf.format(new Date()));

            if (inputDate.before(today)) {
                throw new IllegalStateException("오늘 이전의 날짜는 입력할 수 없습니다");
            }

            return inputDate;
        } catch (DateTimeParseException | ParseException e) {
            throw new IllegalArgumentException("올바르지 않은 날짜 형식입니다", e);
        }
    }


}
