package io.openleap.mps.service;

import io.openleap.mps.model.message.TelegramMessage;
import io.openleap.mps.service.telegram.TelegramService;
import io.openleap.mps.service.telegram.processor.TelegramProcessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

class TelegramProcessorServiceTest {

    private TelegramService telegramService;
    private TelegramProcessorService telegramProcessorService;

    @BeforeEach
    void setUp() {
        telegramService = mock(TelegramService.class);
        telegramProcessorService = new TelegramProcessorService(telegramService);
    }

    @Test
    void listen_shouldCallTelegramServiceSend() {
        TelegramMessage message = mock(TelegramMessage.class);
        when(telegramService.send(message)).thenReturn(Mono.empty());

        telegramProcessorService.listen(message);

        verify(telegramService, times(1)).send(message);
    }
}