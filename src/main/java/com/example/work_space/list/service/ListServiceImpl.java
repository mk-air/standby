package com.example.work_space.list.service;

import com.example.work_space.board.entity.Board;
import com.example.work_space.board.BoardRepository;
import com.example.work_space.list.dto.ListRequestDto;
import com.example.work_space.list.dto.ListResponseDto;
import com.example.work_space.list.dto.ListUpdateRequestDto;
import com.example.work_space.list.entity.List;
import com.example.work_space.list.repository.ListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ListServiceImpl implements ListService {

    private final ListRepository listRepository;
    private final BoardRepository boardRepository;

    public ListServiceImpl(ListRepository listRepository, BoardRepository boardRepository) {
        this.listRepository = listRepository;
        this.boardRepository = boardRepository;
    }

    @Transactional
    @Override
    public ListResponseDto createList(ListRequestDto requestDto) {
        // Board 조회
        Board board = boardRepository.findById(requestDto.getBoardId()).orElseThrow();

        Long count = listRepository.countByBoardId(board.getId());

        List list = List.builder()
                .board(board)
                .title(requestDto.getTitle())
                .seq(++count)
                .build();

        List savedList = listRepository.save(list);

        return new ListResponseDto(savedList);
    }

    @Transactional
    @Override
    public ListResponseDto updateList(Long listId, ListUpdateRequestDto updateRequestDto) {
        List list = listRepository.findById(listId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리스트를 찾을 수 없습니다. ID: " + listId));

        // 엔티티 업데이트
        list.updateTitle(updateRequestDto.getTitle());

        if(updateRequestDto.getSeq() != list.getSeq()) {

            //리스트 리스트가 나옴
            java.util.List<List> lists = listRepository.findByBoardIdOrderBySeqAsc(updateRequestDto.getBoardId());

            /**
             * 수정된 시퀀스가 기존 시퀀스보다 클 경우,
             * 기존 시퀀스 초과 ~ 수정된 시퀀스 이하의 순서를 1씩 낮춰줌
             */
            if(updateRequestDto.getSeq() > list.getSeq()) {
                lists.stream()
                        .filter(l -> l.getSeq() > list.getSeq())
                        .filter(l -> l.getSeq() <= updateRequestDto.getSeq())
                        .forEach(l -> l.downSeq()); // 람다식 => List::downSeq
            } else { // 수정된 시퀀스가 기존 시퀀스보다 작을 경우, 기존 시퀀스 미만 ~ 수정된 시퀀스 이상
                lists.stream()
                        .filter(l -> l.getSeq() < list.getSeq())
                        .filter(l -> l.getSeq() >= updateRequestDto.getSeq())
                        .forEach(l -> l.upSeq());
            }

            list.updateSeq(updateRequestDto.getSeq()); // 기존 순서를 바뀔 순서로 변경
        }
        List updateList = listRepository.save(list);
        return new ListResponseDto(updateList);
    }

    @Override
    public void deleteList(Long listId) {

        List list = listRepository.findById(listId)
                .orElseThrow(() -> new IllegalArgumentException("해당 보드가 존재하지 않습니다. ID: " + listId));

        // 리스트 삭제
        listRepository.delete(list);
    }
}
