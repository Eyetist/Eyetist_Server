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
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;

    public User() {
    }
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
