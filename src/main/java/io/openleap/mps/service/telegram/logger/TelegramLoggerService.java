package io.openleap.mps.service.telegram.logger;

import io.openleap.mps.model.message.TelegramMessage;
import io.openleap.mps.service.telegram.TelegramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Profile("logger")
@Service
public class TelegramLoggerService implements TelegramService {
    Logger logger = LoggerFactory.getLogger(TelegramLoggerService.class);

    @Override
    public Mono<String> send(TelegramMessage telegramMessage) {
        logger.debug("Sending telegramMessage: {}", telegramMessage);
        return Mono.fromCallable(() -> {
            logger.info("Telegram message sent: {}", telegramMessage);
            return "Message sent successfully";
        }).doOnError(error -> {
            logger.error("Error sending telegram message: {}", error.getMessage(), error);
        });
    }
}
