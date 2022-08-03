package inc.ast.test.service;

import inc.ast.test.model.user.User;
import inc.ast.test.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    public DefaultUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
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
}
