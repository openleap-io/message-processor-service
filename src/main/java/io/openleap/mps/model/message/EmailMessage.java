package io.openleap.mps.model.message;


import io.openleap.mps.model.EmailChannel;
import io.openleap.mps.model.Message;

public class EmailMessage {
    Message message;
    String recipientId;
    EmailChannel channel;

    public EmailMessage(Message message, String recipientId, EmailChannel channel) {
        this.message = message;
        this.recipientId = recipientId;
        this.channel = channel;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public EmailChannel getChannel() {
        return channel;
    }

    public void setChannel(EmailChannel channel) {
        this.channel = channel;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }
}
