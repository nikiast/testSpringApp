package inc.ast.test.util;

import inc.ast.test.model.user.User;
import inc.ast.test.repository.UserRepo;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {
    private final UserRepo userRepo;
    private static final String PASSWORD_VALID = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[\\w]{6,}";
    private static final String EMAIL_VALID = "\\w+([\\.-]?\\w+)*@(\\w{2,5}[\\.]\\w{2,3})";

    public UserValidator(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userRepo.findByUsername(user.getUsername()).isPresent() || user.getUsername().equals("")) {
            errors.rejectValue("username", "", "This username is exist or not valid");
        }
        if(!emailValidate(user.getEmail())){
            errors.rejectValue("email", "", "This email is not valid");
        }
        if (!passwordValidate(user.getPassword())){
            errors.rejectValue("password", "", "This password is not valid");
        }
    }

    public boolean usernameValidate(String username) {
        return userRepo.findByUsername(username).isEmpty() && !username.equals("");
    }

    private boolean passwordValidate(String password){
        return Pattern.matches(PASSWORD_VALID, password);
    }

    private boolean emailValidate(String email){
        return Pattern.matches(EMAIL_VALID, email);
    }
}