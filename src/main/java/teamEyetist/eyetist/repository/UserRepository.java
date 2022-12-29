package teamEyetist.eyetist.repository;

import teamEyetist.eyetist.entity.User;

import java.util.Optional;

public interface UserRepository {

    public String save(User user);
    public Optional<User> find(String email);
    public void modify();
    public void delete();
}
