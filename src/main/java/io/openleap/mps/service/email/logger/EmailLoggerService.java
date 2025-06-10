package io.openleap.mps.service.email.logger;

import io.openleap.mps.model.message.EmailMessage;
import io.openleap.mps.service.email.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("logger")
@Service
public class EmailLoggerService implements EmailService {
    Logger logger = LoggerFactory.getLogger(EmailLoggerService.class);

    @Override
    public void send(EmailMessage emailRequest) {
        logger.info("Sending email: {}", emailRequest);
    }
}
