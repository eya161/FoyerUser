package tn.esprit.foyeruniversiteeya.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.foyeruniversiteeya.entities.User;
import tn.esprit.foyeruniversiteeya.services.IUserService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/authenticate")
public class JWTController {
    private final JWTService jwtService;
    @Autowired
    private final IUserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public String getTokenForAuthenticatedUser(@RequestBody JWTAuthenticationRequest authRequest){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()){
            User user = userService.loadUserByUsername(authRequest.getEmail()); // Fetch the user from the service
            return jwtService.generateToken(user);
        }
        else {
            throw new UserNotFoundException("Invalid user credentials");
        }
    }
}