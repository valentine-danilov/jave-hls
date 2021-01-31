package com.epam.hls.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RenderRule {

    @JsonProperty("width")
    private Integer width;

    @JsonProperty("height")
    private Integer height;

    @JsonProperty("videoBitrate")
    private Integer videoBitrate;

    @JsonProperty("audioBitrate")
    private Integer audioBitrate;

}
