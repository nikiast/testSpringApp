package inc.ast.test.controllers;

import inc.ast.test.entitys.user.Role;
import inc.ast.test.entitys.user.User;
import inc.ast.test.repos.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
public class RegistrationController {
    private UserRepo userRepo;

    public RegistrationController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam(name="username") String username, @RequestParam(name="password") String password,
                          Model model) {
        User newUser = new User(username, password, true, Role.USER);
        User userFromDb = userRepo.findByUsername(newUser.getUsername());

        if (newUser.equals(userFromDb)) {
            model.addAttribute("message", "User exists!");
            return "registration";
        }

        userRepo.save(newUser);
        return "redirect:/login";
    }
}