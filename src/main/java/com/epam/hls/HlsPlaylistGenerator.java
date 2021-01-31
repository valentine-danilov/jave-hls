package com.epam.hls;

import com.epam.hls.builder.AudioAttributesBuilder;
import com.epam.hls.builder.EncodingAttributesBuilder;
import com.epam.hls.builder.VideoAttributesBuilder;
import com.epam.hls.entity.RenderRule;
import com.epam.hls.util.AudioCodec;
import com.epam.hls.util.FileSystemUtils;
import com.epam.hls.util.VideoCodec;
import com.epam.hls.util.VideoFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.epam.hls.util.Assertions.assertNotEmpty;
import static com.epam.hls.util.Assertions.assertNotNull;

@Getter
@Setter
@Slf4j
public class HlsPlaylistGenerator {

    private static final long DEFAULT_SEGMENT_DURATION = 2;

    private final Encoder encoder;

    /**
     * Output video codec
     * Default is H_264
     */
    private VideoCodec outputVideoCodec;

    /**
     * Output video codec
     * Default os AAC
     */
    private AudioCodec outputAudioCodec;

    /**
     * Output video format
     * Default is MP4
     */
    private VideoFormat outputVideoFormat;

    /**
     * Enable segmentation or not
     * Default is false
     */
    private boolean segmentationEnabled;

    /**
     * Segment duration (in seconds) if segmentation is enabled
     * Default is 2.0
     */
    private float segmentDuration;

    public HlsPlaylistGenerator() {
        encoder = new Encoder();
        outputVideoCodec = VideoCodec.H_264;
        outputAudioCodec = AudioCodec.AAC;
        outputVideoFormat = VideoFormat.MP4;
        segmentationEnabled = false;
        segmentDuration = DEFAULT_SEGMENT_DURATION;
    }

    public HlsPlaylistGenerator(VideoCodec videoCodec, AudioCodec audioCodec, VideoFormat videoFormat) {
        assertNotNull(videoCodec, "Output Video Codec should not be null");
        assertNotNull(audioCodec, "Output Audio Codec should not be null");
        assertNotNull(videoFormat, "Output Video Format should not be null");

        this.outputVideoCodec = videoCodec;
        this.outputAudioCodec = audioCodec;
        this.outputVideoFormat = videoFormat;

        encoder = new Encoder();
        segmentationEnabled = false;
        segmentDuration = DEFAULT_SEGMENT_DURATION;
    }


    public void generate(File source, File outputDirectory, List<RenderRule> renderRules) throws IOException, EncoderException {
        assertNotNull(source, "Source video file should not be null");
        assertNotNull(outputDirectory, "Output directory should not be null");
        assertNotEmpty(renderRules, "List of render rules should not be empty");

        FileSystemUtils.prepareDirectories(source, outputDirectory);

        MultimediaObject sourceObject = new MultimediaObject(source);
        if (segmentationEnabled) {
            generateWithSegmentation(sourceObject, outputDirectory, renderRules, segmentDuration);
        } else {
            generateWithoutSegmentation(sourceObject, outputDirectory, renderRules);
        }
    }

    /**
     * Generates resized videos
     *
     * @param source          source video object
     * @param outputDirectory output directory where to store created videos
     * @param renderRules     list of rules how to render videos for the HLS protocol
     * @throws EncoderException in case if any exception occurred while rendering videos
     * @throws IOException      in case of any file system related exceptions
     */
    private void generateWithoutSegmentation(MultimediaObject source, File outputDirectory, List<RenderRule> renderRules) throws IOException, EncoderException {
        for (RenderRule renderRule : renderRules) {
            EncodingAttributes attributes = getAttributes(renderRule);
            attributes.setEncodingThreads(8);
            attributes.setDecodingThreads(8);
            encoder.encode(source, FileSystemUtils.getTargetFile(source.getFile(), outputDirectory, renderRule), attributes);
        }
    }

    /**
     * Generates resized videos divided in segments
     *
     * @param source          source video object
     * @param outputDirectory output directory where to store created videos
     * @param renderRules     list of rules how to render videos for the HLS protocol
     * @param segmentDuration duration of segment
     * @throws EncoderException in case if any exception occurred while rendering videos
     * @throws IOException      in case of any file system related exceptions
     */
    private void generateWithSegmentation(MultimediaObject source, File outputDirectory, List<RenderRule> renderRules, float segmentDuration) throws EncoderException, IOException {
        for (RenderRule renderRule : renderRules) {
            generateSegments(source, outputDirectory, renderRule, segmentDuration);
        }
    }

    private void generateSegments(MultimediaObject source, File outputDirectory, RenderRule renderRule, float segmentDuration) {

        EncodingAttributes attributes = getAttributes(renderRule);

        long sourceDurationMillis = 0;
        try {
            sourceDurationMillis = source.getInfo().getDuration();
        } catch (EncoderException e) {
            log.error("Unable to get MultimediaObject information");
        }

        float sourceDurationSeconds = ((float) sourceDurationMillis) / 1000;
        float offset = 0;
        long segmentNumber = 0;
        float localSegmentDuration = this.segmentDuration;

        while (offset < sourceDurationSeconds) {
            try {
                File target = FileSystemUtils.getTargetFile(source.getFile(), outputDirectory, renderRule, segmentNumber);

                if (sourceDurationSeconds - offset < segmentDuration) {
                    localSegmentDuration = sourceDurationMillis - offset;
                }

                attributes.setOffset(offset);
                attributes.setDuration(localSegmentDuration);
                encoder.encode(source, target, attributes);
                offset += segmentDuration;
                segmentNumber++;
            } catch (IOException | EncoderException e) {
                log.error("Exception while rendering :: ", e);
            }
        }
    }

    private EncodingAttributes getAttributes(RenderRule renderRule) {
        AudioAttributesBuilder audioAttributesBuilder = new AudioAttributesBuilder();
        audioAttributesBuilder.codec(outputAudioCodec)
                .bitRate(renderRule.getAudioBitrate());

        VideoAttributesBuilder videoAttributesBuilder = new VideoAttributesBuilder();
        videoAttributesBuilder.codec(outputVideoCodec)
                .bitRate(renderRule.getVideoBitrate())
                .size(renderRule.getWidth(), renderRule.getHeight());

        EncodingAttributesBuilder encodingAttributesBuilder = new EncodingAttributesBuilder();
        return encodingAttributesBuilder.format(outputVideoFormat)
                .audioAttributes(audioAttributesBuilder.build())
                .videoAttributes(videoAttributesBuilder.build())
                .build();
    }

}
