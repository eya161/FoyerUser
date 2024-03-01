package tn.esprit.foyeruniversiteeya.registration;

import tn.esprit.foyeruniversiteeya.entities.Role;

public record RegistrationRequest(
        String nomUser,
        String prenomUser,
        Long cin,
        String email,
        String username,
        String password,
        Role role) {
}
