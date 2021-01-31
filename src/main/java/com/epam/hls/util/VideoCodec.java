package com.epam.hls.util;

import lombok.Getter;

public enum VideoCodec {

    HEVC("hevc"),
    H_264("h264");

    @Getter
    private final String codecName;

    VideoCodec(String codecName) {
        this.codecName = codecName;
    }
}
