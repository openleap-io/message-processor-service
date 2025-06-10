package io.openleap.mps.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Email channel model for API request", allOf = Channel.class,
        example =
                """
                        {
                          "channelType": "MAIL",
                          "bcc": "user@domain.com"
                        }
                        """)
public class EmailChannel extends Channel {
    public EmailChannel() {
        this.channelType = ChannelType.EMAIL;
    }

    @Schema(description = "List of email addresses to send to", example = "<EMAIL>")
    @JsonProperty("bcc")
    String bcc;

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }
}
