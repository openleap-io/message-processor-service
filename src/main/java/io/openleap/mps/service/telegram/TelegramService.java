package io.openleap.mps.service.telegram;

import io.openleap.mps.model.message.TelegramMessage;
import reactor.core.publisher.Mono;

public interface TelegramService {
    Mono<String> send(TelegramMessage telegramMessage);
}
