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

    @Column(name = "likesBlobName", nullable = false)
    private String likesBlobName;

    @Column(name = "member", nullable = false)
    private String member;

    @Column(name = "heart", nullable = false)
    private String heart;

    public Likes(){

    }

    public Likes(String likesBlobName, String member, String heart) {
        this.likesBlobName = likesBlobName;
        this.member = member;
        this.heart = heart;
    }
}
