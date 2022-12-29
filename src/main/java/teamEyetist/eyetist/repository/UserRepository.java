package teamEyetist.eyetist.repository;

import org.springframework.ui.Model;
import teamEyetist.eyetist.entity.User;

import java.util.Optional;

public interface UserRepository {

    public String save(Long id);
    public Optional<User> find(Long id);
    public void modify();
    public void delete();
}
