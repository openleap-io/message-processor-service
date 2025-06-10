package io.openleap.mps.model.message;


import io.openleap.mps.model.Message;
import io.openleap.mps.model.TeamsChannel;

public class TeamsMessage {
    Message message;
    String recipientId;
    TeamsChannel channel;

    public TeamsMessage(Message message, String recipientId, TeamsChannel channel) {
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

    public TeamsChannel getChannel() {
        return channel;
    }

    public void setChannel(TeamsChannel channel) {
        this.channel = channel;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }
}
