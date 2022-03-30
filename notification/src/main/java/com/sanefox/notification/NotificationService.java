package com.sanefox.notification;

import com.sanefox.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void send(NotificationRequest notificationRequest) {
        notificationRepository.save(Notification.builder()
                                    .message(notificationRequest.message())
                                    .sender("Sanefox")
                                    .sentAt(LocalDateTime.now())
                                    .toCustomerEmail(notificationRequest.toCustomerEmail())
                                    .toCustomerId(notificationRequest.toCustomerId())
                                    .build()
        );
    }
}
