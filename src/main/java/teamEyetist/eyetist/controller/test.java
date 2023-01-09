package teamEyetist.eyetist.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

    @GetMapping("test")
    @CrossOrigin(origins = "http://localhost:3000")
    public String get(){

        return "https://eyetiststorage.blob.core.windows.net/abcd1234/5b488402-6dfd-48b2-8191-5cc2de8ba02d";
    }

}
