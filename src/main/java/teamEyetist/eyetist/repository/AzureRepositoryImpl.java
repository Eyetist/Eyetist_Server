package teamEyetist.eyetist.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import teamEyetist.eyetist.domain.Azure;
import teamEyetist.eyetist.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

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
    public String findImage(String container, String blob) {
        Azure result = em.createQuery("SELECT I FROM Azure I where I.container = :container AND I.blob = :blob",  Azure.class)
                .setParameter("container", container)
                .setParameter("blob", blob)
                .getSingleResult();
        return null;
    }

    /**
     * 회원의 사진 1장 가져오기
     */
    @Override
    public Azure readImage(String blobname) {
        Azure result = em.find(Azure.class, blobname);

        return result;
    }

    /**
     * 한 회원의 사진 리스트들 가져오기
     */
    @Override
    public List<Azure> readImageList(String container) {
        List<Azure> resultList = em.createQuery("SELECT I FROM Azure I where I.container = :container", Azure.class)
                .setParameter("container", container)
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

    /**
     * 이미지 삭제하는 코드
     */
    @Override
    public String deleteImage(String blobname) {
        em.remove(em.find(Azure.class, blobname));
        return null;
    }
}
