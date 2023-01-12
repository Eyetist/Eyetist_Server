package teamEyetist.eyetist.service;

import teamEyetist.eyetist.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public String joinUser(User user);
    public Optional<User> findUser(String email);
    public String login(String email, String password);
    public void modifyUser();
    public void deleteUser();
}
