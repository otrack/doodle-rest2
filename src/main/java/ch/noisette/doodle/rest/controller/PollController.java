package ch.noisette.doodle.rest.controller;

import ch.noisette.doodle.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by bperroud on 11-Nov-14.
 */

@Controller
@EnableAutoConfiguration
public class PollController {

    @Autowired
    private PollService pollService;

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(PollController.class, args);
    }

}
