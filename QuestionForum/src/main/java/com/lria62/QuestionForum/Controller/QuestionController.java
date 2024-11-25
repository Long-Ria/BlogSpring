package com.lria62.QuestionForum.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions")

public class QuestionController {


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Home Page - Project_MVC");
        return "question/index";
    }
    @GetMapping("/details/{questionId}")
    public String questionDetails(Model model) {
        model.addAttribute("title", "Home Page - Project_MVC");
        return "question/details";
    }
}
