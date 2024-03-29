package inc.ast.test.controller;

import inc.ast.test.model.user.User;
import inc.ast.test.service.UserService;
import inc.ast.test.util.UserValidator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

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
    public String userSettings(@ModelAttribute("userFromSession") User userFromSession, Model model) {
        model.addAttribute("user", userFromSession);
        return "user/profile";
    }

    @PostMapping("updateUserUsername")
    public String updateUsername(@ModelAttribute("userFromSession") @Valid User userFromSession,
                                 BindingResult bindingResult) {
        userValidator.usernameValidate(userFromSession, bindingResult);
        if (bindingResult.hasErrors()) {
            return "redirect:/user/profile";
        }

        userService.saveUser(userFromSession);
        SecurityContextHolder.clearContext();
        return "redirect:/user/profile";
    }

    @PostMapping("updateUserEmail")
    public String updateEmail(@ModelAttribute("userFromSession") @Valid User userFromSession,
                              BindingResult bindingResult) {
        userValidator.emailValidate(userFromSession, bindingResult);
        if (bindingResult.hasErrors()) {
            return "redirect:/user/profile";
        }
        userService.saveUser(userFromSession);
        SecurityContextHolder.clearContext();
        return "redirect:/user/profile";
    }

    @PostMapping("updateUserPassword")
    public String updatePassword(@ModelAttribute("userFromSession") @Valid User userFromSession,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/user/profile";
        }
        userService.encodePassword(userFromSession);
        userService.saveUser(userFromSession);
        SecurityContextHolder.clearContext();
        return "redirect:/user/profile";
    }

    @GetMapping("deleteUser")
    public String userEditForm(@ModelAttribute("userFromSession") @Valid User userFromSession) {
        userFromSession.setActive(false);
        userService.saveUser(userFromSession);
        SecurityContextHolder.clearContext();
        return "redirect:/";
    }

    @ModelAttribute("userFromSession")
    public User getUserFromSession() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}