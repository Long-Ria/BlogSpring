package com.lria62.QuestionForum.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class UserController {


    @GetMapping("/login")
    public String login() {
        return "user/login";  // Trả về view login.html
    }

    @GetMapping("/register")
    public String register() {
        return "user/register";  // Trả về view register.html
    }

    // Trang profile người dùng
    @GetMapping("/profile")
    public String profile() {

        return "user/profile";  // Trả về trang profile.html
    }
}
