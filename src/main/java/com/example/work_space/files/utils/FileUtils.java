package com.example.work_space.files.utils;

import com.example.work_space.files.constants.FileConst;
import com.example.work_space.files.entity.AttachFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
public class FileUtils {
    private static String path = FileConst.FILE_UPLOAD_PATH;

    public AttachFile saveOneFile(MultipartFile multipartFile) {
        AttachFile attachFile = new AttachFile(multipartFile.getOriginalFilename(), path);

        String folderPath = getFolderPath(attachFile);

        String savedFilePath = folderPath + File.separator + attachFile.getSaved_name() + "." + attachFile.getExt();

        Path savePath = Paths.get(savedFilePath);
        try {
            multipartFile.transferTo(savePath);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장에 실패했습니다.");
        }

        return attachFile;
    }


    private static String getFolderPath(AttachFile img) {
        File folder = new File(img.getPath());
        if (!folder.exists()) {
            folder.mkdir();
        }
        return folder.getPath();
    }
}
