package com.sanefox.customer;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email) {
}
