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
public class Likes {
    @Id
    @Column(name = "blobName", nullable = false)
    private String blobName;

    @Column(name = "member", nullable = false)
    private String member;

    @Column(name = "heart", nullable = false)
    private String heart;

    public Likes(){

    }

    public Likes(String blobName, String member, String heart) {
        this.blobName = blobName;
        this.member = member;
        this.heart = heart;
    }
}
