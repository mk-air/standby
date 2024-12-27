package com.example.work_space.files.service.impl;

import com.example.work_space.board.entity.Board;
import com.example.work_space.files.constants.FileConst;
import com.example.work_space.files.entity.AttachFile;
import com.example.work_space.files.repository.AttachFileRepository;
import com.example.work_space.files.service.AttachFileService;
import com.example.work_space.files.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class AttachFileServiceImpl implements AttachFileService {

    private final AttachFileRepository attachFileRepository;
    private final FileUtils fileUtils;

    @Override
    public AttachFile createAttachFile(Board board, MultipartFile file) {
        if (file == null) {
            return null;
        }
        //파일 검증
        validateMultiPartFile(file);

        AttachFile savedOneFile = fileUtils.saveOneFile(file);
        savedOneFile.updateBoard(board);

        attachFileRepository.save(savedOneFile);

        return savedOneFile;
    }

    // 파일 검증
    private static void validateMultiPartFile(MultipartFile file) {
        // 파일이 없을 경우 예외 처리
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 없습니다.");
        }

        String originalFilename = file.getOriginalFilename();
        // 파일 이름이 없을 경우 예외 처리
        if (originalFilename == null) {
            throw new IllegalArgumentException("파일 이름은 비어있을 수 없습니다.");
        }


        String ext = getExt(originalFilename);
        validateExt(ext);
    }

    private static String getExt(String originalFilename) {
        String[] split = originalFilename.split("\\.");
        String ext = split[split.length - 1];
        return ext;
    }

    private static void validateExt(String ext) {
        if (!FileConst.EXTENSIONS.contains(ext)) {
            throw new IllegalArgumentException("지원하지 않는 파일 형식입니다.");
        }
    }
}
