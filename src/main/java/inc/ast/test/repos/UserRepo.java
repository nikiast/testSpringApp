package inc.ast.test.repos;

import inc.ast.test.entitys.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {

}