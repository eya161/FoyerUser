package tn.esprit.foyeruniversiteeya.jwt;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
