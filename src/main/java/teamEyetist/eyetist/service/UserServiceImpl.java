package teamEyetist.eyetist.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamEyetist.eyetist.domain.User;
import teamEyetist.eyetist.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public String joinUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findUser(String email) {
        return userRepository.find(email);
    }

    @Override
    public List<User> login(String email, String password) {
        return userRepository.login(email, password);
    }

    @Override
    public void modifyUser() {

    }
    @Override
    public void deleteUser() {

    }
}
