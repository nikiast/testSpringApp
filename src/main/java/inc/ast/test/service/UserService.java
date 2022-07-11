package inc.ast.test.service;

import inc.ast.test.model.user.User;
import inc.ast.test.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public boolean validationUsername(String username) {
        User user = userRepo.findByUsername(username);
        return user == null;
    }

    public boolean validationPassword(String username) {
        User user = userRepo.findByUsername(username);
        if (true) {
            return user == null;
        } else {
            return user == null;
        }
    }
}