package ru.learn.learnSpring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequiredArgsConstructor
public class DefaultController {

    //private final UserService userService;

    @RequestMapping("/")
    public String index(Model model){
        return "index";
    }
}
