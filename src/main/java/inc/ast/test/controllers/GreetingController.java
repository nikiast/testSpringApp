package inc.ast.test.controllers;

import inc.ast.test.entitys.User;
import inc.ast.test.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public String index(Model model) {
        Iterable<User> userList = userRepo.findAll();
        model.addAttribute("users", userList);
        return "index";
    }

    @PostMapping
    public String add(@RequestParam(name="login") String login, @RequestParam(name="password") String password,
                        @RequestParam(name="name") String name, Model model){
        User user = new User(name, login, password);
        userRepo.save(user);

        Iterable<User> userList = userRepo.findAll();
        model.addAttribute("users", userList);
        return "index";
    }
}