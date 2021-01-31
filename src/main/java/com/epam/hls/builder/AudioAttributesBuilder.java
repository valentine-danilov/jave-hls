package com.epam.hls.builder;

import com.epam.hls.util.AudioCodec;
import ws.schild.jave.encode.AudioAttributes;

public class AudioAttributesBuilder {

    private final AudioAttributes audioAttributes = new AudioAttributes();

    public AudioAttributes build() {
        return audioAttributes;
    }

    public AudioAttributesBuilder codec(AudioCodec audioCodec) {
        audioAttributes.setCodec(audioCodec.getCodecName());
        return this;
    }

    public AudioAttributesBuilder bitRate(Integer bitRate) {
        audioAttributes.setBitRate(bitRate);
        return this;
    }

    public AudioAttributesBuilder channelNumber(Integer channelNumber) {
        audioAttributes.setChannels(channelNumber);
        return this;
    }

    public AudioAttributesBuilder samplingRate(Integer samplingRate) {
        audioAttributes.setSamplingRate(samplingRate);
        return this;
    }

    public AudioAttributesBuilder volume(Integer volume) {
        audioAttributes.setVolume(volume);
        return this;
    }

    public AudioAttributesBuilder quality(Integer quality) {
        audioAttributes.setQuality(quality);
        return this;
    }

}
