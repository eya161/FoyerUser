package tn.esprit.foyeruniversiteeya.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idUser;
    String email;
    String username;
    String password;
    @Enumerated(EnumType.STRING)
    Role role;
    String nomUser;
    String prenomUser;
    Long cin;
    //@Temporal(TemporalType.DATE)
    Date dateNaissance;
    //to tell if it is blocked or not
    Boolean isEnabled=false;
}
