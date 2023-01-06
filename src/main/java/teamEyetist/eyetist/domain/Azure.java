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

    @Column(name = "id", nullable = false)
    private String id;

    @Id
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

    public Azure(String id, String title, String link, Long likes, String setting) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.likes = likes;
        this.setting = setting;
    }
}
