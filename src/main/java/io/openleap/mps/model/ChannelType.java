package io.openleap.mps.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Type of notification channel")
public enum ChannelType {
    EMAIL,
    SLACK,
    TEAMS,
    TELEGRAM;

}
