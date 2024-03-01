package tn.esprit.foyeruniversiteeya.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.foyeruniversiteeya.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Optional<User> findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
}
