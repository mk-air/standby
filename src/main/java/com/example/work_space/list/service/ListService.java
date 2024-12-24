package com.example.work_space.list.service;

import com.example.work_space.list.dto.ListRequestDto;
import com.example.work_space.list.dto.ListResponseDto;
import com.example.work_space.list.dto.ListUpdateRequestDto;
import jakarta.validation.Valid;

public interface ListService {
    ListResponseDto createList(@Valid ListRequestDto requestDto);
    ListResponseDto updateList(Long listId, ListUpdateRequestDto requestDto);
    void deleteList(Long listId);
}
