package com.example.work_space.card.service;

import com.example.work_space.card.dto.CardDetailResponseDto;
import com.example.work_space.card.dto.CardRequestDto;
import com.example.work_space.card.dto.CardResponseDto;
import com.example.work_space.card.dto.CardSearchRequestDto;
import com.example.work_space.card.entity.Card;
import com.example.work_space.card.repository.CardRepository;
import com.example.work_space.files.entity.AttachFile;
import com.example.work_space.files.service.AttachFileService;
import com.example.work_space.list.repository.ListRepository;
import com.example.work_space.member.entity.Member;
import com.example.work_space.member.repository.MemberRepository;
import com.example.work_space.workspace.type.WorkSpaceRole;
import com.example.work_space.workspace_member.entity.WorkSpaceMember;
import com.example.work_space.workspace_member.repository.WorkSpaceMemberRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    private final MemberRepository memberRepository;
    private final WorkSpaceMemberRepository workSpaceMemberRepository;
    private final ListRepository listRepository;
    private final CardRepository cardRepository;
    private final AttachFileService attachFileService;

    public CardServiceImpl(MemberRepository memberRepository, WorkSpaceMemberRepository workSpaceMemberRepository, ListRepository listRepository, CardRepository cardRepository, AttachFileService attachFileService) {
        this.memberRepository = memberRepository;
        this.workSpaceMemberRepository = workSpaceMemberRepository;
        this.listRepository = listRepository;
        this.cardRepository = cardRepository;
        this.attachFileService = attachFileService;
    }


    @Transactional
    @Override
    public CardResponseDto createCard(CardRequestDto requestDto, Long authId, MultipartFile file) {
        Member member = null;
        com.example.work_space.list.entity.List list = listRepository.findById(requestDto.getListId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 리스트입니다."));
        hasAccess(list, authId);
        if (!StringUtils.isEmpty(requestDto.getMember())) {
            // 사용자 확인
             member = memberRepository.findByEmail(requestDto.getMember())
                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자입니다."));
        }
        Date inputDate = isBeforeToday(requestDto.getDeadline());

        Card card = Card.builder()
                .title(requestDto.getTitle())
                .contents(requestDto.getContents())
                .deadline(inputDate)
                .member(member)
                .list(list)
                .build();

        Card savedCard = cardRepository.save(card);
        AttachFile attachFile = attachFileService.createAttachFile(file);
        attachFile.updateCard(savedCard);
        savedCard.updateAttachFile(attachFile);

        return new CardResponseDto(savedCard);
    }

    @Override
    public CardResponseDto updateCard(CardRequestDto requestDto, Long cardId, Long authId, MultipartFile file) {
        Member member = null;
        if (StringUtils.isEmpty(requestDto.getTitle())
                && StringUtils.isEmpty(requestDto.getContents())
                && StringUtils.isEmpty(requestDto.getDeadline())
                && StringUtils.isEmpty(requestDto.getMember())) {
            throw new IllegalStateException("수정할 내용을 입력해주세요");
        }

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 카드입니다"));
        hasAccess(card.getList(), authId);
        if (!StringUtils.isEmpty(requestDto.getMember())) {
            // 사용자 확인
            member = memberRepository.findByEmail(requestDto.getMember())
                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자입니다."));
        }
        Date inputDate = isBeforeToday(requestDto.getDeadline());

        Card updatedCard = card.update(requestDto.getTitle(), requestDto.getContents(), inputDate, member);


        attachFileService.deleteAttachFile(updatedCard.getAttachFiles().get(0).getId());

        AttachFile attachFile = attachFileService.createAttachFile(file);
        attachFile.updateCard(updatedCard);
        updatedCard.updateAttachFile(attachFile);

        return new CardResponseDto(updatedCard);
    }

    @Override
    public CardDetailResponseDto getCardDetail(Long cardId) {

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 카드입니다"));

        return new CardDetailResponseDto(card);
    }

    @Override
    public void deleteCard(Long cardId, Long authId) {

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 카드입니다"));

        hasAccess(card.getList(), authId);

        cardRepository.delete(card);
    }

    @Override
    public List<CardResponseDto> searchCard(CardSearchRequestDto requestDto) {
        List<Card> cardList;
        if (StringUtils.isEmpty(requestDto.getSearchWord())) {
            cardList = cardRepository.findAll();
        } else {
            switch (requestDto.getSortBy()) {
                case "제목" -> cardList = cardRepository.findAllByTitle(requestDto.getSearchWord());
                case "내용" -> cardList = cardRepository.findAllByContents(requestDto.getSearchWord());
                case "마감일" -> {
                    Date inputDate = isValidDateFormat(requestDto.getSearchWord());
                    cardList = cardRepository.findAllByDeadline(inputDate);
                }
                case "담당자" -> {
                    Member member = memberRepository.findByEmail(requestDto.getSearchWord())
                            .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자입니다."));
                    cardList = cardRepository.findAllByMember(member);
                }
                default -> throw new IllegalStateException("올바른 검색 기준을 설정해주세요");
            }
        }
        return cardList.stream()
                .map(CardResponseDto::new)
                .collect(Collectors.toList());
    }

    public static Date isValidDateFormat(String inputDate) {
        Date date;
        String DATE_PATTERN = "yyyy-MM-dd";

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        sdf.setLenient(false); // 엄격한 날짜 검증

        try {
            // 입력된 날짜를 파싱하여 검증
            date = sdf.parse(inputDate);
            return date;
        } catch (ParseException e) {
            throw new IllegalArgumentException("올바르지 않은 날짜 형식입니다", e);
        }
    }


    public static Date isBeforeToday(String inputDate) {
        if (inputDate == null) {
            return null;
        }
        Date date = isValidDateFormat(inputDate);

        String DATE_PATTERN = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);

        try {
            // 오늘 날짜를 가져오기 (시간 제외)
            Date today = sdf.parse(sdf.format(new Date()));
            if (date.before(today)) {
                return date;
            } else {
                throw new IllegalStateException("오늘 이후의 날짜만 입력 가능합니다");
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Unexpected error during date parsing", e);
        }
    }

    public void hasAccess(com.example.work_space.list.entity.List list, Long authId) {
        Long workSpaceId = list.getBoard().getWorkSpace().getId();
        Optional<WorkSpaceMember> workSpaceMember = workSpaceMemberRepository.findByWorkSpaceIdAndMemberId(workSpaceId, authId);
        WorkSpaceRole role;
        if (workSpaceMember.isPresent()) {
            role = workSpaceMember.get().getRole();
        } else {
            throw new IllegalStateException("조회할 수 없는 워크스페이스 멤버입니다");
        }

        if (role == WorkSpaceRole.READ_ONLY) {
            throw new SecurityException("권한이 없는 워크스페이스 멤버입니다");
        }
    }
}
