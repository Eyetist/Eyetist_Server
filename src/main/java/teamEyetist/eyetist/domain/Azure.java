package teamEyetist.eyetist.domain;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Component
@Getter
@Setter
@DynamicUpdate
public class Azure {

    @Column(name = "member", nullable = false)
    private String member;

    @Id
    @Column(name = "blobName", nullable = false)
    private String blobName;

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

    public Azure() {

    }

    public Azure(String member, String blobName, String title, String link, Long likes, String visibility, String date) {
        this.member = member;
        this.blobName = blobName;
        this.title = title;
        this.link = link;
        this.likes = likes;
        this.visibility = visibility;
        this.date = date;
    }
}
