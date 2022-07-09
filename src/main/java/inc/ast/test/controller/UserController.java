package inc.ast.test.controller;

import inc.ast.test.model.user.User;
import inc.ast.test.repository.UserRepo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static inc.ast.test.controller.RegistrationController.validationUsername;

@Controller
@RequestMapping("/user")
public class UserController {
    UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/settings")
    public String userSettings(@AuthenticationPrincipal User userFromSession,
                               Model model) {
        if (userFromSession != null) {
            model.addAttribute("user", userFromSession);
        } else {
            model.addAttribute("error", "error");
        }
        return "user/settings";
    }

    @PostMapping("updateUserUsername")
    public String updateUsername(@AuthenticationPrincipal User userFromSession,
                                 @RequestParam String username,
                                 Model model) {
        if (validationUsername(username) && userFromSession != null) {
            userFromSession.setUsername(username);
            userRepo.save(userFromSession);
            SecurityContextHolder.clearContext();
        } else {
            model.addAttribute("usernameError", "usernameError!");
        }
        return "redirect:/user/settings";
    }

    @PostMapping("updateUserPassword")
    public String updatePassword(@AuthenticationPrincipal User userFromSession,
                                 @RequestParam String password,
                                 Model model) {
        if (userFromSession != null) {
            userFromSession.setPassword(password);
            userRepo.save(userFromSession);
            SecurityContextHolder.clearContext();
        } else {
            model.addAttribute("passwordError", "passwordError!");
        }
        return "redirect:/user/settings";
    }

    @GetMapping("deleteUser")
    public String userEditForm(@AuthenticationPrincipal User userFromSession) {
        userFromSession.setActive(false);
        userRepo.save(userFromSession);
        SecurityContextHolder.clearContext();
        return "redirect:/";
    }
}