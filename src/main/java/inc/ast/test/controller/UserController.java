package inc.ast.test.controller;

import inc.ast.test.model.user.User;
import inc.ast.test.service.UserService;
import inc.ast.test.util.UserValidator;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserValidator userValidator;

    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
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
        if (userValidator.usernameValidate(username)) {
            userFromSession.setUsername(username);
            userService.userSave(userFromSession);
            SecurityContextHolder.clearContext();
        } else {
            model.addAttribute("usernameError", "usernameError!");
        }
        return "redirect:/user/profile";
    }

    @PostMapping("updateUserEmail")
    public String updateEmail(@AuthenticationPrincipal User userFromSession,
                              @RequestParam String email) {
        userFromSession.setEmail(email);
        userService.userSave(userFromSession);
        SecurityContextHolder.clearContext();
        return "redirect:/user/profile";
    }

    @PostMapping("updateUserPassword")
    public String updatePassword(@AuthenticationPrincipal User userFromSession,
                                 @RequestParam String password) {
        userFromSession.setPassword(password);
        userService.userSave(userFromSession);
        SecurityContextHolder.clearContext();
        return "redirect:/user/profile";
    }

    @GetMapping("deleteUser")
    public String userEditForm(@AuthenticationPrincipal User userFromSession) {
        userFromSession.setActive(false);
        userService.userSave(userFromSession);
        SecurityContextHolder.clearContext();
        return "redirect:/";
    }
}