package io.openleap.mps.service.teams.processor;

import io.openleap.mps.config.RabbitMQConfig;
import io.openleap.mps.model.message.TeamsMessage;
import io.openleap.mps.service.teams.TeamsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TeamsProcessorService {
    Logger logger = LoggerFactory.getLogger(TeamsProcessorService.class);
    private final TeamsService teamsService;

    public TeamsProcessorService(TeamsService teamsService) {
        this.teamsService = teamsService;
    }

    @RabbitListener(queues = RabbitMQConfig.TEAMS_QUEUE)
    public void listen(TeamsMessage teamsMessage) {
        logger.debug("Processing teams message: {}", teamsMessage);
        teamsService.send(teamsMessage);
    }
}
