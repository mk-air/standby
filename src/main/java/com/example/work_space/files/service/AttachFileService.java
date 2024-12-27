package com.example.work_space.files.service;

import com.example.work_space.files.entity.AttachFile;
import org.springframework.web.multipart.MultipartFile;

public interface AttachFileService {
    AttachFile createImgFile(MultipartFile file);
    AttachFile createAttachFile(MultipartFile file);

    void deleteAttachFile(Long imgFileId);
}
