package teamEyetist.eyetist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import teamEyetist.eyetist.entity.User;
import teamEyetist.eyetist.service.UserServiceImpl;
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
    public String joinMember(@RequestParam Long id){
        Optional<User> userCheck = userService.findUser(id);

        if (userCheck.isPresent()) { // 이미 존재하는 이메일
            return "이미 존재하는 이메일입니다.";
        }
        else{ // 회원가입 완료

            return userService.joinUser(id);
        }
    }

    @GetMapping("user/{id}")
    public String findMember(@PathVariable Long id){
        //이메일이 존재하지 않을 시 NPE반환
        Optional<User> user = userService.findUser(id);

        if (user.isPresent()) { // 이메일 반환
            return user.get().getId().toString();
        }
        else{ // 존재하지 않는 이메일
            return "존재하지 않는 이메일입니다.";
        }
    }
}
