package teamEyetist.eyetist.controller;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import teamEyetist.eyetist.domain.User;
import teamEyetist.eyetist.service.UserService;

import java.util.Optional;

@RestController
@Transactional
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("user/join")
    public String joinMember(@RequestParam String email, @RequestParam String password){
        Optional<User> userCheck = userService.findUser(email);

        if (userCheck.isPresent()) { // 이미 존재하는 이메일
            return "이미 존재하는 이메일입니다.";
        }
        else{ // 회원가입 완료
            User user = new User(email, password);
            return userService.joinUser(user);
        }
    }

    @GetMapping("user/{email}")
    public String findMember(@PathVariable String email){
        //이메일이 존재하지 않을 시 NPE반환
        Optional<User> user = userService.findUser(email);

        if (user.isPresent()) { // 이메일 반환
            return user.get().getEmail();
        }
        else{ // 존재하지 않는 이메일
            return "존재하지 않는 이메일입니다.";
        }
    }
}
