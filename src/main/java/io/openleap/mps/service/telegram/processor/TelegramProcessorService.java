package io.openleap.mps.service.telegram.processor;

import io.openleap.mps.config.RabbitMQConfig;
import io.openleap.mps.model.message.TelegramMessage;
import io.openleap.mps.service.telegram.TelegramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TelegramProcessorService {
    Logger logger = LoggerFactory.getLogger(TelegramProcessorService.class);
    private final TelegramService telegramService;

    public TelegramProcessorService(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @RabbitListener(queues = RabbitMQConfig.TELEGRAM_QUEUE)
    public void listen(TelegramMessage telegramMessage) {
        logger.debug("Processing telegramMessage message: {}", telegramMessage);
        telegramService.send(telegramMessage).subscribe(response -> {
            logger.debug("Telegram message sent successfully");
        }, error -> {
            logger.error("Error sending telegram message: {}", error.getMessage(), error);
        });
    }
}
