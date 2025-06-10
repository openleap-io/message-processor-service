package io.openleap.mps.model.message;


import io.openleap.mps.model.Message;
import io.openleap.mps.model.SlackChannel;

public class SlackMessage {
    Message message;
    String recipientId;
    SlackChannel channel;


    public SlackMessage(Message message, String recipientId, SlackChannel channel) {
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

    public SlackChannel getChannel() {
        return channel;
    }

    public void setChannel(SlackChannel channel) {
        this.channel = channel;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }
}
