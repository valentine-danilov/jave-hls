package com.epam.hls;

import com.epam.hls.entity.RenderRule;
import com.epam.hls.util.VideoCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ws.schild.jave.EncoderException;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@SpringBootApplication
@Slf4j
public class Main {

    public static void main(String[] args) throws IOException, EncoderException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<RenderRule>> renderRuleListType = new TypeReference<List<RenderRule>>() {
        };
        List<RenderRule> renderRuleList = objectMapper.readValue(new File("src/main/resources/manifest.json"), renderRuleListType);

        HlsPlaylistGenerator generator = new HlsPlaylistGenerator();
        generator.setSegmentationEnabled(true);
        generator.setSegmentDuration(4);

        Instant start = Instant.now();
        generator.generate(new File("src/main/resources/Forest.mp4"), new File("src/main/resources"), renderRuleList);
        Instant end = Instant.now();

        log.debug("TIME ELAPSED: {}", Duration.between(start, end));

    }
}
