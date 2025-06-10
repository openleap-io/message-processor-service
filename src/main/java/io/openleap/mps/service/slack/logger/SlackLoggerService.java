package io.openleap.mps.service.slack.logger;

import io.openleap.mps.model.message.SlackMessage;
import io.openleap.mps.service.slack.SlackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class SlackLoggerService implements SlackService {
    Logger logger = LoggerFactory.getLogger(SlackLoggerService.class);

    @Override
    public void send(SlackMessage slackMessage) {
        logger.debug("Slack message sent successfully");
    }
}
