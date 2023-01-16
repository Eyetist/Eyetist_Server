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
    public String receiveHeart(String likesBlobName, String member, String heart) {

        if(heart.equals("0")){ // 좋아요 없을 때
            storeHeart(likesBlobName, member);
            return "add";
        }

        else{ // 좋아요 있을 때
            deleteHeart(likesBlobName, member);
            return "minus";
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
    public void storeHeart(String likesBlobName, String member) {
        Likes like = new Likes(likesBlobName, member, "1");
        em.persist(like);
    }

    @Override
    public void deleteHeart(String likesBlobName, String member) {
        em.createQuery("DELETE FROM Likes L where L.likesBlobName = :likesBlobName AND L.member = :member")
                .setParameter("likesBlobName", likesBlobName)
                .setParameter("member", member)
                .executeUpdate();
    }
}
