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
        return "200";
    }

    @Override
    public Optional<User> find(String id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public String login(String id, String password) {
        List<User> users = em.createQuery("SELECT u FROM User u where u.id = :id AND u.password = :password")
                .setParameter("id", id)
                .setParameter("password", password)
                .getResultList();

          if (users.isEmpty()){
              System.out.println("회원이 없음 == 로그인 실패");
              return "400";
          }
          else {
              System.out.println("회원 == 로그인");
              return "200";
          }
    }

    @Override
    public void modify() {

    }

    @Override
    public void delete() {

    }
}
