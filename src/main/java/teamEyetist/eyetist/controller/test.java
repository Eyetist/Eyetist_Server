package teamEyetist.eyetist.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class test {

    @GetMapping("/")
    @CrossOrigin(origins = "*")
   //CrossOrigin(origins = "http://localhost:3000")
    public String get(@RequestParam String test){

        SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd");
        String timeStamp = date.format(new Date());

        return "200";
    }

    @GetMapping("/test")
    @CrossOrigin(origins = "*")
    //CrossOrigin(origins = "http://localhost:3000")
    public String gettest(@RequestParam String test){

        SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd");
        String timeStamp = date.format(new Date());

        return "200";
    }
}
