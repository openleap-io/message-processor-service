package io.openleap.mps.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Slack channel model for API request", allOf = Channel.class,
        example =
                """
                        {
                          "channelType": "SLACK",
                          "channelId": "C1234567890",
                          "channelName": "general"
                        }
                        """)
public class SlackChannel extends Channel {
    public SlackChannel() {
        this.channelType = ChannelType.SLACK;
    }

    @Schema(description = "Slack channel ID", example = "C1234567890")
    @JsonProperty("channelId")
    private String channelId;

    @Schema(description = "Slack channel name", example = "general")
    @JsonProperty("channelName")
    private String channelName;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
