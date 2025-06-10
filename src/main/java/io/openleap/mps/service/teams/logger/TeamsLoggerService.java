package io.openleap.mps.service.teams.logger;

import io.openleap.mps.model.message.TeamsMessage;
import io.openleap.mps.service.teams.TeamsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class TeamsLoggerService implements TeamsService {
    Logger logger = LoggerFactory.getLogger(TeamsLoggerService.class);

    @Override
    public void send(TeamsMessage teamsMessage) {
        logger.debug("Teams message sent successfully");
    }
}
