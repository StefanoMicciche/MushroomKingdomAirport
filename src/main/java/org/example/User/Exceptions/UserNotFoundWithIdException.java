package org.example.User.Exceptions;

public class UserNotFoundWithIdException extends RuntimeException {
    public UserNotFoundWithIdException(String message) {
        super(message);
    }
}
