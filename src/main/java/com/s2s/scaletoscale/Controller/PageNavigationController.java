package com.s2s.scaletoscale.Controller;

import com.s2s.scaletoscale.models.request.Blog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class PageNavigationController {
    @Value("${article}")
    String article;

    @GetMapping("/")
    public String home(Model model){
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model){
        return "about";
    }

    @GetMapping("/faq")
    public String faq(Model model){
        return "faq";
    }

    @GetMapping("/blog-post")
    public String blogPost(Model model){
        return "blog-post";
    }

    @GetMapping("/course-blog-post")
    public String courseBlogPost(Model model){
        model.addAttribute("variable",article);
        return "course-blog-post";
    }

    @GetMapping("/blog-home")
    public String blogHome(Model model){
        return "blog-home";
    }

    @GetMapping("/contact")
    public String contact(Model model){
        return "contact";
    }

    @GetMapping("/pricing")
    public String pricing(Model model){
        return "pricing";
    }

    @GetMapping("/write-blog")
    public String writeBlog(@ModelAttribute Blog blog, Model model){
        return "admin/write-blog";
    }
}
