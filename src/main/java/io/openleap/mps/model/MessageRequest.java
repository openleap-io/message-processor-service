package io.openleap.mps.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Message model for API request")
public class MessageRequest {
    @Schema(description = "List of recipients")
    @JsonProperty(value = "recipients", required = true)
    List<Recipient> recipients;

    @Schema(description = "Message content")
    @JsonProperty(value = "message", required = true)
    Message message;

    public List<Recipient> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<Recipient> recipients) {
        this.recipients = recipients;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
