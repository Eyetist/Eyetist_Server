package teamEyetist.eyetist.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Component
@Getter
@Setter
public class AzureDTO {
    @Column(name = "member", nullable = false)
    private String member;

    @Id
    @Column(name = "azureBlobName", nullable = false)
    private String azureBlobName;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "link", nullable = false)
    private String link;

    @Column(name = "likes", nullable = false)
    private Long likes;

    @Column(name = "visibility", nullable = false)
    private String visibility;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "likesBlobName", nullable = false)
    private String likesBlobName;

    @Column(name = "heart", nullable = false)
    private String heart;
}
