package teamEyetist.eyetist.repository;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import teamEyetist.eyetist.domain.Azure;
import teamEyetist.eyetist.domain.AzureDTO;

import javax.persistence.EntityManager;
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
    public void increaseLikes(String azureBlobName) {
        em.createQuery("UPDATE Azure A SET A.likes = A.likes + 1 WHERE A.azureBlobName = : azureBlobName")
                .setParameter("azureBlobName", azureBlobName)
                .executeUpdate();
    }
    @Override
    public void decreaseLikes(String azureBlobName) {
        em.createQuery("UPDATE Azure A SET A.likes = A.likes - 1 WHERE A.azureBlobName = : azureBlobName")
                .setParameter("azureBlobName", azureBlobName)
                .executeUpdate();
    }

    /**
     * 회원의 사진 1장 가져오기
     */
    @Override
    public Azure readImage(String blobName) {
        Azure result = em.find(Azure.class, blobName);
        return result;
    }

    /**
     * 한 회원의 사진 리스트들 가져오기
     */
    @Override
    public List<AzureDTO> readImageList(String member) {
        List<Azure> resultList = em.createQuery("SELECT I FROM Azure I where I.member = :member", Azure.class)
                .setParameter("member", member)
                .getResultList();

        List<AzureDTO> result = em.createNativeQuery("SELECT * from Azure AS A LEFT JOIN (SELECT L.likesBlobName, L.heart from Likes L WHERE L.member = :member) AS B ON A.azureBlobName = B.likesBlobName WHERE A.member = :member", AzureDTO.class)
                .setParameter("member", member)
                .getResultList();
        return result;
    }

    /**
     * 공개된 사진 리스트들 가져오드
     */
    @Override
    public List<AzureDTO> readPublicImageList(String visibility, int page, String member) {
        List<AzureDTO> result = em.createNativeQuery("SELECT * from Azure AS A LEFT JOIN (SELECT L.likesBlobName, L.heart from Likes L WHERE L.member = :member) AS B ON A.azureBlobName = B.LblobName WHERE A.visibility = :visibility", AzureDTO.class)
                .setParameter("visibility", visibility)
                .setParameter("member", member)
                .setFirstResult(page * 10)   //시작 위치 지정
                .setMaxResults(10) //조회할 데이터 개수 지정
                .getResultList();

        return result;
    }

    /**
     * 퍼블릭 이미지 수 세기
     * @return
     */
    @Override
    public Long imageCount() {
        Long result = em.createQuery("select count(A) from Azure A where A.visibility = :visibility", Long.class)
                .setParameter("visibility", "public")
                .getSingleResult();
        return result;
    }

    @Override
    public String deleteUserStore() {
        return null;
    }

    /**
     * 이미지 삭제하는 코드
     */
    @Override
    public String deleteImage(String azureBlobName) {
        em.remove(em.find(Azure.class, azureBlobName));
        return null;
    }
}
