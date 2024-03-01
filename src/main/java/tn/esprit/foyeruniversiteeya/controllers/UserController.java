package tn.esprit.foyeruniversiteeya.controllers;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import tn.esprit.foyeruniversiteeya.entities.User;
import tn.esprit.foyeruniversiteeya.event.RegistrationCompleteEvent;
import tn.esprit.foyeruniversiteeya.services.IUserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;
    private final ApplicationEventPublisher publisher;

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }
    @GetMapping("/getUserDetails/{id}")
    public User getUser(@PathVariable("id") Long id){ return userService.getUserById(id);}
    @PostMapping("/addUser")
    public User addUser(@RequestBody User user, final HttpServletRequest request) {
        User addedUser = userService.addUser(user);
        // Send verification email for the added user
        publisher.publishEvent(new RegistrationCompleteEvent(addedUser, applicationUrl(request)));
        return addedUser;
    }
    /*@PatchMapping("/updateUser/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") Long id){ return userService.updateUser(user, id);}*/
    @PutMapping("/updateUser")
    public User updateUser(@RequestBody User user){ return userService.updateUser(user);}
    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable("id") Long id){ userService.deleteUser(id);}
    public String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"
                    +request.getServerPort()+request.getContextPath();
    }
}
