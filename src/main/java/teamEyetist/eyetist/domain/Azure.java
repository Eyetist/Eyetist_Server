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
public class Azure {

    @Column(name = "container", nullable = false)
    private String container;

    @Id
    @Column(name = "blobname", nullable = false)
    private String blob;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "link", nullable = false)
    private String link;

    @Column(name = "likes", nullable = false)
    private Long likes;

    @Column(name = "setting", nullable = false)
    private String setting;

    public Azure() {

    }


    public Azure(String container, String blob, String title, String link, Long likes, String setting) {
        this.container = container;
        this.blob = blob;
        this.title = title;
        this.link = link;
        this.likes = likes;
        this.setting = setting;
    }
}
