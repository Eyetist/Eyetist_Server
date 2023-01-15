package teamEyetist.eyetist.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
@Getter
@Setter
public class Likes {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "number", nullable = false)
    private Long number;

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
