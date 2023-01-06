package teamEyetist.eyetist.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import teamEyetist.eyetist.domain.User;

import javax.persistence.EntityManager;
import java.util.List;
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
    public Optional<User> find(String id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public List<User> login(String id, String password) {
        List<User> users = em.createQuery("SELECT u FROM User u where u.id = :id AND u.password = :password")
                .setParameter("id", id)
                .setParameter("password", password)
                .getResultList();
        /**
         * if (users.isEmpty()){
         *             System.out.println("asdasd");
         *         }
         *         else System.out.println("hello = ");
         */
        return users;
    }

    @Override
    public void modify() {

    }

    @Override
    public void delete() {

    }
}
