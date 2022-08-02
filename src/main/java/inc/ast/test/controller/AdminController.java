package inc.ast.test.controller;

import inc.ast.test.model.user.Role;
import inc.ast.test.model.user.User;
import inc.ast.test.service.UserService;
import inc.ast.test.util.UserValidator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final UserValidator userValidator;

    public AdminController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping
    public String userList(Model model) {
        Iterable<User> userList = userService.findAllUser();
        model.addAttribute("users", userList);
        return "user/admin/userList";
    }

    @PostMapping("filter")
    public String filterByUsername(@RequestParam String filter, Model model) {
        if (filter != null && !filter.isEmpty()) {
            Optional<User> userFromDb = userService.findUserByUsername(filter);
            userFromDb.ifPresent(user -> model.addAttribute("users", user));
        } else {
            model.addAttribute("users", userService.findAllUser());
        }
        return "user/admin/userList";
    }

    @GetMapping("{id}")
    public String userEditForm(@PathVariable("id") User user, Model model) {
        model.addAttribute("user", user);
        if (user.isActive()) {
            model.addAttribute("Active", true);
        } else {
            model.addAttribute("notActive", true);
        }
        return "user/admin/userEdit";
    }

    @PostMapping("updateUsername/{id}")
    public String updateUsername(@PathVariable("id") User user, @RequestParam String username, Model model) {
        if (userValidator.usernameValidate(username)) {
            user.setUsername(username);
            userService.userSave(user);
        } else {
            model.addAttribute("usernameExists", "Username exists!");
        }
        return "redirect:/admin/{id}";
    }


    @PostMapping("updatePassword/{id}")
    public String updatePassword(@PathVariable("id") User user, @RequestParam String password) {
        user.setPassword(password);
        userService.userSave(user);
        return "redirect:/admin/{id}";
    }

    @PostMapping("updateRole/{id}")
    public String updateRole(@PathVariable("id") User user, @RequestParam String role) {
        switch (role) {
            case "USER" -> user.setRole(Role.USER);
            case "PROVIDER" -> user.setRole(Role.PROVIDER);
            case "ADMIN" -> user.setRole(Role.ADMIN);
        }
        userService.userSave(user);
        return "redirect:/admin/{id}";
    }

    @GetMapping("lockUser/{id}")
    public String lockUser(@PathVariable("id") User user) {
        user.setActive(false);
        userService.userSave(user);
        return "redirect:/admin/{id}";
    }

    @GetMapping("unlockUser/{id}")
    public String unlockUser(@PathVariable("id") User user) {
        user.setActive(true);
        userService.userSave(user);
        return "redirect:/admin/{id}";
    }
}