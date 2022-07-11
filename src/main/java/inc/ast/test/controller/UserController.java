package inc.ast.test.controller;

import inc.ast.test.model.user.User;
import inc.ast.test.repository.UserRepo;
import inc.ast.test.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserRepo userRepo;
    private UserService userService;

    public UserController(UserRepo userRepo, UserService userService) {
        this.userRepo = userRepo;
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String userSettings(@AuthenticationPrincipal User userFromSession,
                               Model model) {
        model.addAttribute("user", userFromSession);
        return "user/profile";
    }

    @PostMapping("updateUserUsername")
    public String updateUsername(@AuthenticationPrincipal User userFromSession,
                                 @RequestParam String username,
                                 Model model) {
        if (userService.validationUsername(username)) {
            userFromSession.setUsername(username);
            userRepo.save(userFromSession);
            SecurityContextHolder.clearContext();
        } else {
            model.addAttribute("usernameError", "usernameError!");
        }
        return "redirect:/user/profile";
    }

    @PostMapping("updateUserEmail")
    public String updateEmail(@AuthenticationPrincipal User userFromSession,
                              @RequestParam String email) {
        userFromSession.setPassword(email);
        userRepo.save(userFromSession);
        SecurityContextHolder.clearContext();
        return "redirect:/user/profile";
    }

    @PostMapping("updateUserPassword")
    public String updatePassword(@AuthenticationPrincipal User userFromSession,
                                 @RequestParam String password) {
        userFromSession.setPassword(password);
        userRepo.save(userFromSession);
        SecurityContextHolder.clearContext();
        return "redirect:/user/profile";
    }

    @GetMapping("deleteUser")
    public String userEditForm(@AuthenticationPrincipal User userFromSession) {
        userFromSession.setLock(false);
        userRepo.save(userFromSession);
        SecurityContextHolder.clearContext();
        return "redirect:/";
    }
}