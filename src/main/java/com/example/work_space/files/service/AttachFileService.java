package com.example.work_space.files.service;

import com.example.work_space.board.entity.Board;
import com.example.work_space.files.entity.AttachFile;
import org.springframework.web.multipart.MultipartFile;

public interface AttachFileService {
    AttachFile createAttachFile(Board board, MultipartFile file);
}
