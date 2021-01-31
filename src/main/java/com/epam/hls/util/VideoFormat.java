package com.epam.hls.util;

import lombok.Getter;

public enum VideoFormat {

    MP4("mp4");

    @Getter
    private final String format;

    VideoFormat(String format) {
        this.format = format;
    }
}
