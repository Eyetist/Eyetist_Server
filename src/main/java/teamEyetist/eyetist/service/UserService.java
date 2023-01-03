package teamEyetist.eyetist.service;

import teamEyetist.eyetist.domain.User;

import java.util.Optional;

public interface UserService {
    public String joinUser(User user);
    public Optional<User> findUser(String email);
    public void modifyUser();
    public void deleteUser();
}
