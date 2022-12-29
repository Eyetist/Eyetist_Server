package teamEyetist.eyetist.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import teamEyetist.eyetist.entity.User;
import teamEyetist.eyetist.repository.UserRepositoryImpl;
import teamEyetist.eyetist.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public String joinUser(Long id) {
        return userRepository.save(id);
    }

    @Override
    public Optional<User> findUser(Long id) {
        return userRepository.find(id);
    }

    @Override
    public void modifyUser() {

    }

    @Override
    public void deleteUser() {

    }
}
