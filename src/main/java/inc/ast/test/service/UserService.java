package inc.ast.test.service;

import inc.ast.test.model.user.Role;
import inc.ast.test.model.user.User;
import inc.ast.test.repository.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    public User createUser(User user) {
        user.setRole(Role.USER);
        user.setRegistrationTime(formatDateTimeNow());
        user.setActive(true);
        return user;
    }

    @Transactional
    public void saveUser(User user) {
        userRepo.save(user);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public List<User> findAllUser() {
        return userRepo.findAll();
    }

    public User selectRole(User user, String role){
        switch (role) {
            case "USER" -> user.setRole(Role.USER);
            case "PROVIDER" -> user.setRole(Role.PROVIDER);
            case "ADMIN" -> user.setRole(Role.ADMIN);
        }
        return user;
    }

    public void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    public String formatDateTimeNow() {
        LocalDateTime registrationTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return registrationTime.format(format);
    }
}