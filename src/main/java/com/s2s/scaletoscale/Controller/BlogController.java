package com.s2s.scaletoscale.Controller;

import com.s2s.scaletoscale.models.request.Blog;
import com.s2s.scaletoscale.service.BlogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private ModelMapper modelMapper;


    @GetMapping("/")
    public String getBlogs(Model theModel){
        theModel.addAttribute("blogs",blogService.getAllBlogs());
        return "blog-home";
    }

    @PostMapping("/preview-blog")
    public String previewBlog(@ModelAttribute Blog blog, Model model){
        model.addAttribute("blog",blog);
        return "preview-blog";
    }

    @PostMapping("/save-blog")
    public String saveBlog(@ModelAttribute Blog blog, Model model){
        model.addAttribute("blog",blog);
        return "preview-blog";
    }
}
