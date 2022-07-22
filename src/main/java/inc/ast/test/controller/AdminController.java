package inc.ast.test.controller;

import inc.ast.test.model.user.Role;
import inc.ast.test.model.user.User;
import inc.ast.test.repository.UserRepo;
import inc.ast.test.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    private final UserRepo userRepo;
    private final UserService userService;

    public AdminController(UserRepo userRepo, UserService userService) {
        this.userRepo = userRepo;
        this.userService = userService;
    }

    @GetMapping
    public String userList(Model model) {
        Iterable<User> userList = userRepo.findAll();
        model.addAttribute("users", userList);
        return "user/admin/userList";
    }

    @PostMapping("filter")
    public String filterByUsername(@RequestParam String filter, Model model) {
        List<User> users;
        if (filter != null && !filter.isEmpty()) {
            users = Stream.of(userRepo.findByUsername(filter)).toList();
        } else {
            users = userRepo.findAll();
        }
        model.addAttribute("users", users);
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
        if (userService.validationUsername(username)) {
            user.setUsername(username);
            userRepo.save(user);
        } else {
            model.addAttribute("userExists", "User exists!");
        }
        return "redirect:/admin/{id}";
    }

    @PostMapping("updateEmail/{id}")
    public String updateEmail(@PathVariable("id") User user, @RequestParam String email, Model model) {
        user.setEmail(email);
        userRepo.save(user);
        return "redirect:/admin/{id}";
    }

    @PostMapping("updatePassword/{id}")
    public String updatePassword(@PathVariable("id") User user, @RequestParam String password) {
        user.setPassword(password);
        userRepo.save(user);
        return "redirect:/admin/{id}";
    }

    @PostMapping("updateRole/{id}")
    public String updateRole(@PathVariable("id") User user, @RequestParam String role) {
        switch (role) {
            case "USER" -> user.setRole(Role.USER);
            case "PROVIDER" -> user.setRole(Role.PROVIDER);
            case "ADMIN" -> user.setRole(Role.ADMIN);
        }
        userRepo.save(user);
        return "redirect:/admin/{id}";
    }

    @GetMapping("lockUser/{id}")
    public String lockUser(@PathVariable("id") User user) {
        user.setActive(false);
        userRepo.save(user);
        return "redirect:/admin/{id}";
    }

    @GetMapping("unlockUser/{id}")
    public String unlockUser(@PathVariable("id") User user) {
        user.setActive(true);
        userRepo.save(user);
        return "redirect:/admin/{id}";
    }
}