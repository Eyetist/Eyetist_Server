package teamEyetist.eyetist.domain;


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
    private String id;
    @Column(name = "password", nullable = false)
    private String password;

    public User() {
    }
    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
