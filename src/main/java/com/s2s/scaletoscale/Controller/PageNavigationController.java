package com.s2s.scaletoscale.Controller;

import com.s2s.scaletoscale.models.request.Blog;
import com.s2s.scaletoscale.models.response.Course;
import com.s2s.scaletoscale.service.BlogService;
import com.s2s.scaletoscale.service.CourseService;
import com.s2s.scaletoscale.utils.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Optional;

@Controller
public class PageNavigationController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/")
    public String home(Model model){
        List<com.s2s.scaletoscale.models.response.Blog> blogList = blogService.getAllBlogs();
        List<Course> featuredCourses = courseService.getCourses();
        model.addAttribute("featuredBlogs",blogList);
        model.addAttribute("featuredCourses",featuredCourses.get(0));
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
        Optional<com.s2s.scaletoscale.models.response.Blog> blog = blogService.getBlog(2);
        if (blog.isPresent()) {
            com.s2s.scaletoscale.models.response.Blog blogResponse = blog.get();
            model.addAttribute("blog", blogResponse);
        } else {
            model.addAttribute("status", "Blog does not exists");
            return "home";
        }
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

    @GetMapping("/portfolio")
    public String portfolio(Model model){
        return "portfolio-overview";
    }

    @GetMapping("/portfolio-item")
    public String portfolioItem(Model model){
        return "portfolio-item";
    }
/*
    @GetMapping("/error")
    public String error(Model model){
        return "error";
    }
    
 */
}
