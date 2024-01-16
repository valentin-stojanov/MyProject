package com.myproject.project.tasks;

import com.myproject.project.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private final UserRepository userRepository;

    public ScheduledTasks(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "0 * * * * ?", zone = "Europe/Sofia")
    public void deleteAllExpiredPasswordResetTokens() {
        int expirationTimeInSeconds = 240;
        this.userRepository.deleteExpiredPasswordResetToken(expirationTimeInSeconds);
    }
}
