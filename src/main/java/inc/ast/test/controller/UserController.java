package inc.ast.test.controller;

import inc.ast.test.model.user.Role;
import inc.ast.test.model.user.User;
import inc.ast.test.repository.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequestMapping("/user")
public class UserController {
    UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public String userList(Model model) {
        Iterable<User> userList = userRepo.findAll();
        model.addAttribute("users", userList);
        return "user/userList";
    }

    @PostMapping("filter")
    public String filterByUsername(@RequestParam String filter,
                                   Model model) {
            Iterable <User> users;

        if (filter != null && !filter.isEmpty()) {
            users = userRepo.findByUsername(filter);
        } else {
            users = userRepo.findAll();
        }
        model.addAttribute("users", users);
        return "user/userList";
    }

    @GetMapping("{id}")
    public String userEditForm(@PathVariable Long id,
                               Model model) {
        User userFromDB = userRepo.findById(id).get();
        model.addAttribute("user", userFromDB);
        model.addAttribute("role", userFromDB.getRole());
        String role = userFromDB.getRole().toString();
        switch (role){
            case("USER") -> model.addAttribute("USER");
            case("PROVIDER") -> model.addAttribute("PROVIDER");
            case("ADMIN") -> model.addAttribute("ADMIN");
        }
        return "user/userEdit";
    }

    @PostMapping("update/{id}")
    public String updateUser(@PathVariable Long id,
                             @RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String role,
                             Model model) {
        User userFromDB = userRepo.findById(id).get();


            userFromDB.setUsername(username);
            userFromDB.setPassword(password);
            switch (role){
                case ("USER") -> {
                    userFromDB.setRole(Role.USER);
                }
                case ("PROVIDER") -> {
                    userFromDB.setRole(Role.PROVIDER);
                }
                case ("ADMIN") -> {
                    userFromDB.setRole(Role.ADMIN);
                }
            }
        userRepo.save(userFromDB);
        return "redirect:/user";
    }
}
