package teamEyetist.eyetist.controller;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import teamEyetist.eyetist.domain.User;
import teamEyetist.eyetist.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@Transactional
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("user/join")
    //@CrossOrigin(origins = "http://localhost:3000")
    public String joinMember(@RequestParam String id, @RequestParam String password){
        Optional<User> userCheck = userService.findUser(id);

        if (userCheck.isPresent()) { // 이미 존재하는 이메일
            return "400";
        }
        else{ // 회원가입 완료
            User user = new User(id, password);
            return userService.joinUser(user);
        }
    }

    @PostMapping("user/login")
    public String LoginUser(@RequestParam String id, @RequestParam String password){
        return userService.login(id, password);
    }

    @GetMapping("user/{id}")
    public String findMember(@PathVariable String id){
        //이메일이 존재하지 않을 시 NPE반환
        Optional<User> user = userService.findUser(id);

        if (user.isPresent()) { // 이메일 반환
            return user.get().getId();
        }
        else{ // 존재하지 않는 이메일
            return "존재하지 않는 이메일입니다.";
        }
    }
}
