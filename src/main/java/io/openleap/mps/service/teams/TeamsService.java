package io.openleap.mps.service.teams;

import io.openleap.mps.model.message.TeamsMessage;

public interface TeamsService {
    void send(TeamsMessage emailMessage);
}
