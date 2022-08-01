package inc.ast.test.service;

import inc.ast.test.model.user.User;
import inc.ast.test.repository.UserRepo;
import inc.ast.test.util.UserValidator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final UserValidator userValidator;

    public UserService(UserRepo userRepo, UserValidator userValidator) {
        this.userRepo = userRepo;
        this.userValidator = userValidator;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userFromDb = userRepo.findByUsername(username);
        if (userFromDb.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        } else {
            return userFromDb.get();
        }
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public List<User> findAllUser() {
        return userRepo.findAll();
    }

    @Transactional
    public void userSave(User user) {
        userRepo.save(user);
    }

    public String formatDateTimeNow() {
        LocalDateTime registrationTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return registrationTime.format(format);
    }

    public void usernameValidate(User user, Errors errors) {
        userValidator.validate(user, errors);
    }

    public boolean usernameValidate(String username) {
        return userRepo.findByUsername(username).isEmpty();
    }

//    public boolean validationPassword(String username) {
//        User user = userRepo.findByUsername(username);
//        if (true) {
//            return user == null;
//        } else {
//            return user == null;
//        }
//    }
}