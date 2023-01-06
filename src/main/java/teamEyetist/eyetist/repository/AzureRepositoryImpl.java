package teamEyetist.eyetist.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import teamEyetist.eyetist.domain.Azure;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class AzureRepositoryImpl implements AzureRepository{

    private EntityManager em;

    @Autowired
    public AzureRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    /**
     * 사진 이미지 저장
     */
    @Override
    public String storeImage(Azure azure) {
        em.persist(azure);
        return "200";
    }

    @Override
    public String findImage(String title, String id) {
        Azure result = em.createQuery("SELECT I FROM Azure I where I.title = :title AND I.id = :id",  Azure.class)
                .setParameter("title", title)
                .setParameter("id", id)
                .getSingleResult();
        return null;
    }

    /**
     * 회원의 사진 1장 가져오기
     */
    @Override
    public List<Azure> readImage(String title, String id) {
        List<Azure> result = em.createQuery("SELECT I FROM Azure I where I.title = :title AND I.id = :id")
                .setParameter("title", title)
                .setParameter("id", id)
                .getResultList();

        return result;
    }

    /**
     * 한 회원의 사진 리스트들 가져오기
     */
    @Override
    public List<Azure> readImageList(String id) {
        List<Azure> resultList = em.createQuery("SELECT I FROM Azure I where I.id = :id", Azure.class)
                .setParameter("id", id)
                .getResultList();
        return resultList;
    }

    /**
     * 공개된 사진 리스트들 가져오드
     */
    @Override
    public List<Azure> readPublicImageList(String setting) {
        List<Azure> resultList = em.createQuery("SELECT I FROM Azure I where I.setting = :setting", Azure.class)
                .setParameter("setting", setting)
                .getResultList();
        return resultList;
    }

    @Override
    public String deleteUserStore() {
        return null;
    }

    @Override
    public String deleteImage() {
        return null;
    }
}
