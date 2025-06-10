package io.openleap.mps.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Teams channel model for API request", allOf = Channel.class,
        example =
                """
                        {
                          "channelType": "TEAMS",
                          "channelId": "1234567890",
                          "tenantId": "1234567890"
                        }
                        """)
public class TeamsChannel extends Channel {
    public TeamsChannel() {
        this.channelType = ChannelType.TEAMS;
    }

    @Schema(description = "Teams channel ID", example = "1234567890")
    @JsonProperty("channelId")
    String channelId;

    @Schema(description = "Teams tenant ID", example = "1234567890")
    @JsonProperty("tenantId")
    String tenantId;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
