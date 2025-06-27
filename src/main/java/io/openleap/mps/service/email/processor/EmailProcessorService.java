package io.openleap.mps.service.email.processor;

import io.openleap.mps.config.RabbitMQConfig;
import io.openleap.mps.model.message.EmailMessage;
import io.openleap.mps.service.email.EmailService;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EmailProcessorService {
    private final EmailService emailService;
    Logger logger = org.slf4j.LoggerFactory.getLogger(EmailProcessorService.class);

    public EmailProcessorService(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void listen(EmailMessage message) {
        logger.debug("Processing email message: {}", message);
        try {
            emailService.send(message);
        } catch (Exception e) {
            logger.error("Failed to process email message: {}", message, e);
        }
    }
}
