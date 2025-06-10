package io.openleap.mps.service.email;

import io.openleap.mps.model.message.EmailMessage;

public interface EmailService {
    void send(EmailMessage emailMessage);
}
