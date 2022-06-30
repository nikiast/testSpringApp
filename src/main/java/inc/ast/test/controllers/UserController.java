package inc.ast.test.controllers;

import inc.ast.test.entitys.user.User;
import inc.ast.test.repos.UserFilterRepo;
import inc.ast.test.repos.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
public class UserController {
    UserRepo userRepo;
    UserFilterRepo userFilterRepo;

    public UserController(UserRepo userRepo, UserFilterRepo userFilterRepo) {
        this.userRepo = userRepo;
        this.userFilterRepo = userFilterRepo;
    }

    @GetMapping("/list")
    public String userList(Model model) {
        Iterable<User> userList = userRepo.findAll();
        model.addAttribute("users", userList);
        return "userList";
    }

    @PostMapping("filter")
    public String filterByUsername(@RequestParam String filter, Model model) {
            Iterable <User> users;

        if (filter != null && !filter.isEmpty()) {
            users = userFilterRepo.findByUsername(filter);
        } else {
            users = userRepo.findAll();
        }
        model.addAttribute("users", users);
        return "userList";
    }

}
