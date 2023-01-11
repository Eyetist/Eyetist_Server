package teamEyetist.eyetist.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

    @GetMapping("/")
   //CrossOrigin(origins = "http://localhost:3000")
    public String get(@RequestParam String test){
        return test;
    }

}
