package com.sanefox.customer;

import com.sanefox.clients.fraud.FraudCheckResponse;
import com.sanefox.clients.fraud.FraudClient;
import com.sanefox.clients.notification.NotificationClient;
import com.sanefox.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@AllArgsConstructor
public class CustomerService {

    private CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (Objects.requireNonNull(fraudCheckResponse).isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

        notificationClient.sendNotification(new NotificationRequest(customer.getId(), customer.getEmail(),
                String.format("Hi %s", customer.getFirstName())));

    }
}
