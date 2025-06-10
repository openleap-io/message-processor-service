package io.openleap.mps.service.slack.processor;

import io.openleap.mps.config.RabbitMQConfig;
import io.openleap.mps.model.message.SlackMessage;
import io.openleap.mps.service.slack.SlackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class SlackProcessorService {
    private final SlackService slackService;
    Logger logger = LoggerFactory.getLogger(SlackProcessorService.class);

    public SlackProcessorService(SlackService slackService) {
        this.slackService = slackService;
    }

    @RabbitListener(queues = RabbitMQConfig.SLACK_QUEUE)
    public void listen(SlackMessage slackMessage) {
        logger.debug("Processing slack message: {}", slackMessage);
        slackService.send(slackMessage);
    }
}
