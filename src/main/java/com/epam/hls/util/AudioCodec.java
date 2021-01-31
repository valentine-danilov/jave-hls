package com.epam.hls.util;

import lombok.Getter;

public enum AudioCodec {

    AAC("aac");

    @Getter
    private final String codecName;

    AudioCodec(String codecName) {
        this.codecName = codecName;
    }
}
