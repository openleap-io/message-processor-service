package io.openleap.mps.service;

import io.openleap.mps.model.message.TeamsMessage;
import io.openleap.mps.service.teams.TeamsService;
import io.openleap.mps.service.teams.processor.TeamsProcessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class TeamsProcessorServiceTest {

    private TeamsService teamsService;
    private TeamsProcessorService teamsProcessorService;

    @BeforeEach
    void setUp() {
        teamsService = mock(TeamsService.class);
        teamsProcessorService = new TeamsProcessorService(teamsService);
    }

    @Test
    void listen_shouldCallTeamsServiceSend() {
        TeamsMessage message = mock(TeamsMessage.class);

        teamsProcessorService.listen(message);

        verify(teamsService, times(1)).send(message);
    }
}