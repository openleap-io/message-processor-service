package io.openleap.mps.service;


import io.openleap.mps.model.message.EmailMessage;
import io.openleap.mps.service.email.EmailService;
import io.openleap.mps.service.email.processor.EmailProcessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class EmailProcessorServiceTest {

    private EmailService emailService;
    private EmailProcessorService emailProcessorService;

    @BeforeEach
    void setUp() {
        emailService = mock(EmailService.class);
        emailProcessorService = new EmailProcessorService(emailService);
    }

    @Test
    void listen_shouldCallEmailServiceSend() {
        EmailMessage message = mock(EmailMessage.class);

        emailProcessorService.listen(message);

        verify(emailService, times(1)).send(message);
    }
}