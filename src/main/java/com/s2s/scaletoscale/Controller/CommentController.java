package com.s2s.scaletoscale.Controller;

import com.s2s.scaletoscale.models.response.Comment;
import com.s2s.scaletoscale.service.CommentService;
import com.s2s.scaletoscale.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private CommentService commentService;

    @GetMapping
    public String getCommentsByBlogId(@ModelAttribute("comment")com.s2s.scaletoscale.models.request.Comment comment, @RequestParam(value = "blogId", required = true) int blogId, Model model){
        try {
            List<Comment> comments = commentService.getCommentsByBlogId(blogId);
            model.addAttribute("comments", comments);
            model.addAttribute("blogId", blogId);
        }catch (Exception e){
            model.addAttribute("comments", new ArrayList<>());
            model.addAttribute("blogId", blogId);
            LOGGER.error("Error while getting comments of blogId : {}, exception : {}",blogId,e);
        }
        return "comment";
    }

    @GetMapping("/replies")
    public String getRepliesByCommentId(@RequestParam(value = "blogId", required = true) int blogId, @RequestParam(value = "commentId", required = true) int commentId, Model model){
        List<Comment> comments = commentService.getRepliesFromCommentId(blogId,commentId);
        model.addAttribute("comments",comments);
        return "comment";
    }

    @PostMapping("")
    public String saveComment(@ModelAttribute("comment")com.s2s.scaletoscale.models.request.Comment comment,Model model){
        try {
            List<Comment> comments = commentService.saveComment(comment);
            model.addAttribute("comments", comments);
            model.addAttribute("blogId", comment.getBlogId());
        }catch (Exception e){
            model.addAttribute("comments", new ArrayList<>());
            model.addAttribute("blogId", comment.getBlogId());
            LOGGER.error("Error while saving comment for blogId : {}, with exception {}",comment.getBlogId(),e);

        }
        return "comment";
    }
}
