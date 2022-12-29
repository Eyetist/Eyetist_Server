package teamEyetist.eyetist.service;

import org.springframework.ui.Model;
import teamEyetist.eyetist.entity.User;

import java.util.Optional;

public interface UserService {
    public String joinUser(Long id);
    public Optional<User> findUser(Long id);
    public void modifyUser();
    public void deleteUser();
}
