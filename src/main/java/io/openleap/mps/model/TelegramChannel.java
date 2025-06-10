package io.openleap.mps.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Telegram channel model for API request", allOf = Channel.class,
        example =
                """
                        {
                          "channelType": "TELEGRAM",
                          "chatId": "@oltesme"
                        }
                        """)
public class TelegramChannel extends Channel {
    public TelegramChannel() {
        this.channelType = ChannelType.TELEGRAM;
    }

    @Schema(description = "Telegram chat id", example = "@oltesme")
    @JsonProperty("chatId")
    String chatId;

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
