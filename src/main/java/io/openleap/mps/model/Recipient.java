package io.openleap.mps.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Recipient model for API request")
public class Recipient {
    @Schema(description = "Unique identifier of the recipient", example = "kirilarsov@gmail.com", maxLength = 256)
    @JsonProperty(value = "id", required = true)
    String id;

    @Schema(
            description = "Channel object example",
            example =
                    """
                            {
                              "channelType": "TEAMS",
                              "channelId": "1234567890",
                              "tenantId": "1234567890"
                            }
                            """)
    @JsonProperty(value = "channel", required = true)
    Channel channel;

    protected Recipient() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
