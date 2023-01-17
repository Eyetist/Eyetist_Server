package teamEyetist.eyetist.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
@Getter
@Setter
@Builder
@DynamicUpdate
public class Azure {

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

    @Column(name = "weekly", nullable = false)
    private Long weekly;

    public Azure() {

    }

    public Azure(String member, String azureBlobName, String title, String link, Long likes, String visibility, String date,  Long weekly) {
        this.member = member;
        this.azureBlobName = azureBlobName;
        this.title = title;
        this.link = link;
        this.likes = likes;
        this.visibility = visibility;
        this.date = date;
        this.weekly = weekly;
    }
}
