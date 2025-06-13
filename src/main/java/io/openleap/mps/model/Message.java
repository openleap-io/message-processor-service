package io.openleap.mps.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(
        description = "Message interface",
        discriminatorProperty = "messageType",
        subTypes = {CustomMessage.class, TemplateMessage.class}
)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "messageType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CustomMessage.class, names = {"CUSTOM","CustomMessage"}),
        @JsonSubTypes.Type(value = TemplateMessage.class, names = {"TEMPLATE","TemplateMessage"})
})
public abstract class Message {
    @Schema(description = "Message type", example = "TEMPLATE")
    @JsonProperty("messageType")
    MessageType messageType;

    @Schema(description = "Message subject", example = "Test message")
    @JsonProperty("subject")
    String subject;

    @Schema(description = "Message body", example = "This is a test message")
    @JsonProperty("body")
    String body;

    @Schema(description = "List of attachments")
    @JsonProperty("attachments")
    List<Attachment> attachments;

    protected Message() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
