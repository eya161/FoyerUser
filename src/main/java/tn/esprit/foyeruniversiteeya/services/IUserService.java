package tn.esprit.foyeruniversiteeya.services;

import tn.esprit.foyeruniversiteeya.entities.User;
import tn.esprit.foyeruniversiteeya.registration.RegistrationRequest;
import tn.esprit.foyeruniversiteeya.registration.token.VerificationToken;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getUsers();
    User getUserById(Long idUser);
    void deleteUser(Long idUser);
    User addUser(User user);
    User updateUser(User user);
    /*User updateUser(User user, Long idUser);*/
    Optional<User> findByEmail(String email);
    User loadUserByUsername(String email);
    User register(RegistrationRequest request);
    void saveUserVerificationToken(User theUser, String token);
    String validateToken(String theToken);
    VerificationToken generateNewVerificationToken(String oldToken);
    void changePassword(User theUser, String newPassword);
    String validatePasswordResetToken(String token);
    User findUserByPasswordToken(String token);
    void createPasswordResetTokenForUser(User user, String passwordResetToken);
    boolean oldPasswordIsValid(User user, String oldPassword);

}
