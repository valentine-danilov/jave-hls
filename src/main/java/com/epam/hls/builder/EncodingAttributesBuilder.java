package com.epam.hls.builder;

import com.epam.hls.util.VideoFormat;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;

public class EncodingAttributesBuilder {

    private final EncodingAttributes encodingAttributes = new EncodingAttributes();

    public EncodingAttributes build() {
        return encodingAttributes;
    }

    public EncodingAttributesBuilder format(VideoFormat videoFormat) {
        encodingAttributes.setOutputFormat(videoFormat.getFormat());
        return this;
    }

    public EncodingAttributesBuilder audioAttributes(AudioAttributes audioAttributes) {
        encodingAttributes.setAudioAttributes(audioAttributes);
        return this;
    }

    public EncodingAttributesBuilder videoAttributes(VideoAttributes videoAttributes) {
        encodingAttributes.setVideoAttributes(videoAttributes);
        return this;
    }

    public EncodingAttributesBuilder encodingThreads(Integer encodingThreads)  {
        encodingAttributes.setEncodingThreads(encodingThreads);
        return this;
    }

    public EncodingAttributesBuilder decodingThreads(Integer decodingThreads) {
        encodingAttributes.setDecodingThreads(decodingThreads);
        return this;
    }
}
