package com.s2s.scaletoscale.Controller;

import com.s2s.scaletoscale.models.request.Blog;
import com.s2s.scaletoscale.models.response.UserProfile;
import com.s2s.scaletoscale.service.BlogService;
import com.s2s.scaletoscale.service.UserLikeService;
import com.s2s.scaletoscale.service.UserProfileService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("blog")
public class BlogController {

    @Autowired
    private UserLikeService userLikeService;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(BlogController.class);

    @GetMapping("/")
    public String getBlogs(Model theModel){
        theModel.addAttribute("blogs",blogService.getAllBlogs());
        return "user/blog-home";
    }

    @PostMapping("/preview")
    public String previewBlog(@ModelAttribute Blog blog, Model model){
        model.addAttribute("blog",blog);
        return "admin/preview-blog";
    }

    @PostMapping("/save")
    public String saveBlog(@ModelAttribute Blog blog, Model model){
        try {
            com.s2s.scaletoscale.models.response.Blog blogResponse = blogService.saveBlog(blog);
            model.addAttribute("blog", blogResponse);
            model.addAttribute("status", "Blog Saved.");
        }catch (Exception exception){
            model.addAttribute("status","Error during saving blog.");
            logger.error(exception.getMessage());
        }
        return "admin/write-blog";
    }

    @GetMapping("/edit-blog/{blogId}")
    public String editBlog(@ModelAttribute Blog blog, @PathVariable("blogId") int blogId, Model model){
        Optional<com.s2s.scaletoscale.models.response.Blog> blogEntity = blogService.getBlog(blogId);
        if (blogEntity.isPresent()) {
            model.addAttribute("blog", blogEntity.get());
        } else {
            model.addAttribute("status", "Blog does not exists");
            return "list-blog";
        }
        return "admin/edit-blog";
    }
    @Secured({"ROLE_STUDENT","ROLE_ADMIN","ROLE_SUPER_ADMIN"})
    @GetMapping("/like/{blogId}")
    public String toggleUserLike(@PathVariable("blogId") int blogId, Model model){
        Optional<com.s2s.scaletoscale.models.response.Blog> blog = blogService.getBlog(blogId);
        if (blog.isPresent()) {
            userLikeService.toggleUserLike(blogId);
            com.s2s.scaletoscale.models.response.Blog blogResponse = blog.get();
            model.addAttribute("blog", blogResponse);
            model.addAttribute("isLiked",userLikeService.getUserLike(blogId));
            model.addAttribute("likeCount",userLikeService.getTotalLikes(blogId));
        } else {
            model.addAttribute("status", "Blog does not exists");
            return "user/home";
        }
        return "user/blog-post";
    }

    @GetMapping("/{blogId}")
    public String getBlog(@PathVariable("blogId") int blogId, Model model){
        Optional<com.s2s.scaletoscale.models.response.Blog> blog = blogService.getBlog(blogId);
        if (blog.isPresent()) {
            com.s2s.scaletoscale.models.response.Blog blogResponse = blog.get();
            model.addAttribute("blog", blogResponse);
            model.addAttribute("isLiked",userLikeService.getUserLike(blogId));
            model.addAttribute("likeCount",userLikeService.getTotalLikes(blogId));
        } else {
            model.addAttribute("status", "Blog does not exists");
            return "user/home";
        }
        return "user/blog-post";
    }

    @DeleteMapping("/{blogId}")
    public String deleteBlog(@PathVariable("blogId") int blogId, Model model){
        try {
            blogService.deleteBlog(blogId);
            List<com.s2s.scaletoscale.models.response.Blog> blogList = blogService.getAllBlogs();
            model.addAttribute("status", "Blog Deleted id : "+blogId);
            model.addAttribute("blogList", blogList);
        }catch (Exception e){
            model.addAttribute("status","Error during deleting blog.");
            logger.error(e.getMessage());
        }
        return "user/list-blog";
    }
}
