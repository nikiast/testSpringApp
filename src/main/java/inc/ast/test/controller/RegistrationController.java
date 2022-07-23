package inc.ast.test.controller;

import inc.ast.test.model.user.Role;
import inc.ast.test.model.user.User;
import inc.ast.test.repository.UserRepo;
import inc.ast.test.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registration() {
        return "/security/registration";
    }

    @PostMapping
    public String addUser(@RequestParam(name = "username") String username,
                          @RequestParam(name = "email") String email,
                          @RequestParam(name = "password") String password,
                          Model model) {

        if (userService.validationUsername(username)) {
            LocalDateTime registrationTime = LocalDateTime.now();
            User newUser = new User(username, email, password, Role.USER, registrationTime, true);
            userService.userSave(newUser);
            return "redirect:/login";
        } else {
            model.addAttribute("userExists", "User exists!");
            return "/security/registration";
        }
    }
}