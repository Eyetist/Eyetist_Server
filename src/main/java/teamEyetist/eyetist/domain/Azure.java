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
    @Column(name = "image_name", nullable = false)
    private String imageName;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "likes", nullable = false)
    private Long likes;

    @Column(name = "public_check", nullable = false)
    private String publicCheck;

    public Azure() {

    }

    public Azure(String id, String imageName, String imageUrl, Long likes, String publicCheck) {
        this.id = id;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
        this.likes = likes;
        this.publicCheck = publicCheck;
    }
}
