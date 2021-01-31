package com.epam.hls.builder;

import com.epam.hls.util.VideoCodec;
import ws.schild.jave.encode.VideoAttributes;
import ws.schild.jave.encode.enums.X264_PROFILE;
import ws.schild.jave.info.VideoSize;

public class VideoAttributesBuilder {

    private final VideoAttributes videoAttributes = new VideoAttributes();

    public VideoAttributes build() {
        return videoAttributes;
    }

    public VideoAttributesBuilder codec(VideoCodec videoCodec) {
        videoAttributes.setCodec(videoCodec.getCodecName());
        return this;
    }

    public VideoAttributesBuilder x264Profile(X264_PROFILE profile) {
        videoAttributes.setX264Profile(profile);
        return this;
    }

    public VideoAttributesBuilder bitRate(Integer bitRate) {
        videoAttributes.setBitRate(bitRate);
        return this;
    }

    public VideoAttributesBuilder frameRate(Integer frameRate) {
        videoAttributes.setFrameRate(frameRate);
        return this;
    }

    public VideoAttributesBuilder size(Integer width, Integer height) {
        videoAttributes.setSize(new VideoSize(width, height));
        return this;
    }
}
