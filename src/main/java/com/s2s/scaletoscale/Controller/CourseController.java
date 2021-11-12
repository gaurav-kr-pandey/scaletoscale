package com.s2s.scaletoscale.Controller;

import com.s2s.scaletoscale.models.response.Blog;
import com.s2s.scaletoscale.models.response.Course;
import com.s2s.scaletoscale.service.BlogService;
import com.s2s.scaletoscale.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Cacheable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{name}")
    public String getCourse(@RequestParam(name = "id",required = true) int id, Model model){
        Course course =  courseService.getCourseById(id);
        List<Blog> blogs = course.getBlogs();
        model.addAttribute("course",course);
        model.addAttribute("blogs",blogs);
        model.addAttribute("blog",blogs.get(0));
        return "user/course-blog-post";
    }

    @PostMapping("/")
    public String saveCourse(@ModelAttribute("course") com.s2s.scaletoscale.models.request.Course course, Model model){
        Course courseResponse = courseService.saveCourse(course);
        List<Blog> blogs = blogService.getAllBlogs();
        blogs.removeAll(courseResponse.getBlogs());
        model.addAttribute("course",courseResponse);
        model.addAttribute("blogs",blogs);
        return "admin/create-course";
    }

    @GetMapping("/edit/{courseId}")
    public String editCourse(@PathVariable("courseId") int id, @ModelAttribute("course") com.s2s.scaletoscale.models.request.Course course, Model model){
        Course courseResponse = courseService.getCourseById(id);
        List<Blog> blogs = blogService.getAllBlogs();
        blogs.removeAll(courseResponse.getBlogs());
        model.addAttribute("course",courseResponse);
        model.addAttribute("blogs",blogs);
        return "admin/create-course";
    }

    @GetMapping("/chapter")
    public String getCourseChapter(@RequestParam(value = "courseId",required = false) int courseId,@RequestParam(value = "chapterId",required = false) int chapterId,  Model model){
        Course course =  courseService.getCourseById(courseId);
        List<Blog> blogs = course.getBlogs();
        Blog blog = blogs.stream().filter(blog1 -> blog1.getId()==chapterId).collect(Collectors.toList()).get(0);
        model.addAttribute("course",course);
        model.addAttribute("blogs",blogs);
        model.addAttribute("blog",blog);
        return "user/course-blog-post";
    }

    @GetMapping("/create")
    public String createCourse(@ModelAttribute("course") com.s2s.scaletoscale.models.request.Course course, Model model){
        model.addAttribute("blogs",blogService.getAllBlogs());
        return "admin/create-course";
    }

    @GetMapping("/add")
    public String addChapter(@RequestParam(value = "courseId",required = false) int courseId,@RequestParam(value = "blogId",required = false) int blogId,  Model model){
        Course courseResponse = courseService.addChapter(courseId,blogId);
        List<Blog> blogs = blogService.getAllBlogs();
        blogs.removeAll(courseResponse.getBlogs());
        model.addAttribute("course",courseResponse);
        model.addAttribute("blogs",blogs);
        return "create-course";
    }

    @GetMapping("/remove")
    public String removeChapter(@RequestParam(value = "courseId",required = false) int courseId,@RequestParam(value = "blogId",required = false) int blogId,  Model model){
        Course courseResponse = courseService.removeChapter(courseId,blogId);
        List<Blog> blogs = blogService.getAllBlogs();
        blogs.removeAll(courseResponse.getBlogs());
        model.addAttribute("course",courseResponse);
        model.addAttribute("blogs",blogs);
        return "admin/create-course";
    }

}
