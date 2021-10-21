package com.s2s.scaletoscale.Controller;

import com.s2s.scaletoscale.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/")
    public String getBlogs(Model theModel){

        theModel.addAttribute("blogs",blogService.getAllBlogs());
        return "blog-home";
    }
}
