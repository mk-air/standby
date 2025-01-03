package com.example.work_space.files.constants;

import lombok.Getter;

import java.util.Arrays;

public enum AllowFiles {
      JPG(FileTypes.IMG)
    , PNG(FileTypes.IMG)
    , PDF(FileTypes.DOC)
    , CSV(FileTypes.DOC)
    , MP3(FileTypes.MUSIC)
    ;

    @Getter
    final FileTypes fileType;

    AllowFiles(FileTypes fileTypes) {
        this.fileType = fileTypes;
    }

    public static AllowFiles of(String fileName) {
        return Arrays.stream(values())
                .filter(af -> af.name().equalsIgnoreCase(fileName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원 안 함"));
    }
}
