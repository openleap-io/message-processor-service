package io.openleap.mps.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Channel model for API request.",
        discriminatorProperty = "channelType",
        subTypes = {EmailChannel.class, TeamsChannel.class, SlackChannel.class,TeamsChannel.class}
)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "channelType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = EmailChannel.class, names = {"EMAIL", "EmailChannel"}),
        @JsonSubTypes.Type(value = TeamsChannel.class, names = {"TEAMS", "TeamsChannel"}),
        @JsonSubTypes.Type(value = SlackChannel.class, names = {"SLACK", "SlackChannel"}),
        @JsonSubTypes.Type(value = TelegramChannel.class, names = {"TELEGRAM", "TelegramChannel"})
})
public abstract class Channel {
    @Schema(
            description = "Channel type",
            example = "TELEGRAM"
    )
    @JsonProperty(value = "channelType", required = true)
    ChannelType channelType;

    public ChannelType getChannelType() {
        return channelType;
    }

    public void setChannelType(ChannelType channelType) {
        this.channelType = channelType;
    }

    Channel() {
    }
}
