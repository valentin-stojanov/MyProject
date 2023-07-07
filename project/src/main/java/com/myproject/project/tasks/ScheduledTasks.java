package com.myproject.project.tasks;

import com.myproject.project.repository.PasswordResetTokenRepository;
import com.myproject.project.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class ScheduledTasks {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;

    public ScheduledTasks(PasswordResetTokenRepository passwordResetTokenRepository, UserRepository userRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "0 * * * * ?", zone = "Europe/Sofia")
    @Transactional
    public void deleteAllExpiredPasswordResetTokens(){

        int expirationTimeInSeconds = 60;

        this.userRepository.deleteExpiredPasswordResetToken(expirationTimeInSeconds);
    }
}
