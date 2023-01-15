package teamEyetist.eyetist.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import teamEyetist.eyetist.domain.Azure;
import teamEyetist.eyetist.domain.Likes;

import javax.persistence.EntityManager;
import java.util.List;


@Repository
@Transactional
public class LikesRepositoryImpl implements LikesRepository{

    private EntityManager em;

    @Autowired
    public LikesRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void receiveHeart(String blobName, String member, String heart) {

        if(heart.equals("0")){ // 좋아요 없을 때
            storeHeart(blobName, member);
        }

        else{ // 좋아요 있을 때
            deleteHeart(blobName);
        }
    }

    @Override
    public List<Likes> getLikesList(String member) {
        List<Likes> likes = em.createQuery("SELECT L FROM Likes L where L.member = :member", Likes.class)
                .setParameter("member", member)
                .getResultList();
        return likes;
    }

    @Override
    public void storeHeart(String blobName, String member) {
        Likes like = new Likes(blobName, member, "1");
        em.persist(like);
    }

    @Override
    public void deleteHeart(String blobName) {
        em.remove(em.find(Likes.class, blobName));  // 트랜젝션 커밋시 반영됨.
    }
}
