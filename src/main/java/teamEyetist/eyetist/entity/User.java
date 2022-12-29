package teamEyetist.eyetist.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
@Getter
@Setter
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    public User() {
    }
    public User(Long id) {
        this.id = id;
    }
}
