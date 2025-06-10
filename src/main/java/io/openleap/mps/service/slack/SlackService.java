package io.openleap.mps.service.slack;

import io.openleap.mps.model.message.SlackMessage;

public interface SlackService {
    void send(SlackMessage slackMessage);

}
