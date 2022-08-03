package inc.ast.test.controller;

import inc.ast.test.model.user.Role;
import inc.ast.test.model.user.User;
import inc.ast.test.service.UserService;
import inc.ast.test.util.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;


    public RegistrationController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
    }

    @GetMapping
    public String registration(@ModelAttribute("user") User user) {
        return "/security/registration";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) {
        user.setRole(Role.USER);
        user.setRegistrationTime(userService.formatDateTimeNow());
        user.setActive(true);
        userService.regUserValidate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/security/registration";
        }
        userService.userSave(user);
        return "redirect:/login";
    }
}