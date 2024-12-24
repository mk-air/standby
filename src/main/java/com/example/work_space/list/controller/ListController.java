package com.example.work_space.list.controller;

import com.example.work_space.constants.CommonResponse;
import com.example.work_space.list.dto.ListRequestDto;
import com.example.work_space.list.dto.ListResponseDto;
import com.example.work_space.list.dto.ListUpdateRequestDto;
import com.example.work_space.list.service.ListService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lists")
public class ListController {

    private final ListService listService;

    public ListController(ListService listService) {
        this.listService = listService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse<ListResponseDto>> createList(
            @Valid @RequestBody ListRequestDto requestDto) {
        ListResponseDto list = listService.createList(requestDto);
        return ResponseEntity.ok(new CommonResponse<>("리스트 생성", list));
    }

    @PutMapping("/{listId}")
    public ResponseEntity<CommonResponse<ListResponseDto>> updateList(
            @PathVariable Long listId, @RequestBody ListUpdateRequestDto requestDto) {
        ListResponseDto updateList = listService.updateList(listId, requestDto);
        return ResponseEntity.ok(new CommonResponse<>("리스트 수정 성공", updateList));
    }


    @DeleteMapping("/{listId}")
    public ResponseEntity<CommonResponse<Void>> deleteList(@PathVariable Long listId) {
        listService.deleteList(listId);
        return ResponseEntity.ok(new CommonResponse<>("리스트 삭제 완료"));
    }
}
