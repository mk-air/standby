package com.example.work_space.files.service;

import com.example.work_space.files.constants.AllowFiles;
import com.example.work_space.files.constants.FileTypes;
import com.example.work_space.files.entity.AttachFile;
import com.example.work_space.files.repository.AttachFileRepository;
import com.example.work_space.files.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AttachFileServiceImpl implements AttachFileService {

    private final AttachFileRepository attachFileRepository;
    private final FileUtils fileUtils;

    @Override
    @Transactional
    public AttachFile createImgFile(MultipartFile file) {
        //파일 검증
        validateMultiPartFile(file);
        validateImgFile(file);

        AttachFile savedOneFile = fileUtils.saveOneFile(file);

        attachFileRepository.save(savedOneFile);

        return savedOneFile;
    }

    @Override
    @Transactional
    public AttachFile createAttachFile(MultipartFile file) {
        //파일 검증
        validateMultiPartFile(file);
        validateFileExt(file);

        AttachFile savedOneFile = fileUtils.saveOneFile(file);

        attachFileRepository.save(savedOneFile);

        return savedOneFile;

    }

    @Override
    @Transactional
    public void deleteAttachFile(Long imgFileId) {
        AttachFile attachFile = attachFileRepository.findById(imgFileId)
                .orElseThrow(() -> new IllegalArgumentException("해당 파일을 찾을 수 없습니다."));

        attachFile.softDelete();

    }

    // 파일 검증

    private void validateMultiPartFile(MultipartFile file) {
        // 파일이 없을 경우 예외 처리
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 없습니다.");
        } else if (file == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "파일이 없습니다.");

        }
        // 파일 이름이 없을 경우 예외 처리
        if (file.getOriginalFilename() == null) {
            throw new IllegalArgumentException("파일 이름은 비어있을 수 없습니다.");
        }
    }
    private void validateImgFile(MultipartFile file) {
        String ext = getExt(file.getOriginalFilename());
        validateImgExt(ext);
    }
    private void validateFileExt(MultipartFile file) {
        String ext = getExt(file.getOriginalFilename());
        validateImgExt(ext);
        validateDocsExt(ext);
    }

    private void validateExt(String ext, FileTypes fileTypes) {
        AllowFiles.of(ext.toUpperCase());
    }

    private void validateImgExt(String ext) {
        validateExt(ext, FileTypes.IMG);
    }
    private void validateDocsExt(String ext) {
        validateExt(ext, FileTypes.DOC);
    }

    private static String getExt(String originalFilename) {
        String[] split = originalFilename.split("\\.");
        String ext = split[split.length - 1];
        return ext;
    }
}
