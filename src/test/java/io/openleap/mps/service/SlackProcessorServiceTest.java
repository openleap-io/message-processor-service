package io.openleap.mps.service;

import io.openleap.mps.model.message.SlackMessage;
import io.openleap.mps.service.slack.SlackService;
import io.openleap.mps.service.slack.processor.SlackProcessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class SlackProcessorServiceTest {

    private SlackService slackService;
    private SlackProcessorService slackProcessorService;

    @BeforeEach
    void setUp() {
        slackService = mock(SlackService.class);
        slackProcessorService = new SlackProcessorService(slackService);
    }

    @Test
    void listen_shouldCallSlackServiceSend() {
        SlackMessage message = mock(SlackMessage.class);

        slackProcessorService.listen(message);

        verify(slackService, times(1)).send(message);
    }
}