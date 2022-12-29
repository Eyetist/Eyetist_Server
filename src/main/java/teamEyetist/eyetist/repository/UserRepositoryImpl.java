package teamEyetist.eyetist.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import teamEyetist.eyetist.entity.User;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    private EntityManager em;

    @Autowired
    public UserRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public String save(User user) {
        em.persist(user);
        return "회원가입이 완료되었습니다.";
    }

    @Override
    public Optional<User> find(String email) {
        return Optional.ofNullable(em.find(User.class, email));
    }

    @Override
    public void modify() {

    }

    @Override
    public void delete() {

    }
}
