package sptech.school.v2.cleanarch.domain.exception;

public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super(message);
    }
}
