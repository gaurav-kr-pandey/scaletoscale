package com.s2s.scaletoscale.Controller;

import com.s2s.scaletoscale.models.response.Comment;
import com.s2s.scaletoscale.service.CommentService;
import com.s2s.scaletoscale.utils.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private CommentService commentService;

    @GetMapping
    public String getCommentsByBlogId(@ModelAttribute("comment")com.s2s.scaletoscale.models.request.Comment comment,
                                      @RequestParam(value = "blogId", required = true) int blogId,
                                      @RequestParam(value = "offset",defaultValue = "0", required = true) int offset,
                                      Model model){
        if(offset<0) offset=0;
        model.addAttribute("isReply",false);
        try {
            List<Comment> comments = commentService.getCommentsByBlogId(blogId,offset);
            if(comments.isEmpty()){
                offset=0;
                comments = commentService.getCommentsByBlogId(blogId,offset);

            }
            model.addAttribute("offset",offset);
            model.addAttribute("comments", comments);
            model.addAttribute("blogId", blogId);
            model.addAttribute("userId",securityUtils.getLoggedInUserId());
        }catch (Exception e){
            model.addAttribute("comments", new ArrayList<>());
            model.addAttribute("blogId", blogId);
            model.addAttribute("userId",securityUtils.getLoggedInUserId());
            model.addAttribute("offset",offset);
            LOGGER.error("Error while getting comments of blogId : {}, exception : {}",blogId,e);
            return "user/iframe-error";
        }
        return "user/comment";
    }

    @GetMapping("/replies")
    public String getRepliesByCommentId(@ModelAttribute("comment")com.s2s.scaletoscale.models.request.Comment commentReq,
                                        @RequestParam(value = "blogId", required = true) int blogId,
                                        @RequestParam(value = "commentId", required = true) int commentId,
                                        @RequestParam(value = "offset",defaultValue = "0", required = true) int offset, Model model){
        if(offset<0) offset=0;
        try {
            replies(blogId, commentId,offset, model);
            commentReq.setParentId(commentId);
            model.addAttribute("comment",commentReq);
            model.addAttribute("userId",securityUtils.getLoggedInUserId());
        }catch (Exception e){
            LOGGER.error("Error while replies of commentId : {}, exception : {}",commentId,e);
            return "user/iframe-error";
        }
        return "user/comment";
    }

    @GetMapping("/edit")
    public String editCommentById(@ModelAttribute("comment")com.s2s.scaletoscale.models.request.Comment commentReq,
                                  @RequestParam(value = "blogId", required = true) int blogId,
                                  @RequestParam(value = "commentId", required = true) int commentId,
                                  @RequestParam(value = "offset",defaultValue = "0", required = true) int offset,Model model){
        try {
            if(offset<0) offset=0;
            model.addAttribute("userId",securityUtils.getLoggedInUserId());
            Comment comment= commentService.getCommentById(commentId);
            commentReq = modelMapper.map(comment, com.s2s.scaletoscale.models.request.Comment.class);
            model.addAttribute("blogId", blogId);
            model.addAttribute("comment",commentReq);
            List<Comment> comments;
            if(isNotParentComment(comment.getParentId())){
                Comment parentComment = commentService.getCommentById(comment.getParentId());
                model.addAttribute("replyTo",parentComment);
                comments = new ArrayList<>();
                comments.add(parentComment);
                List<Comment> replies = commentService.getRepliesFromCommentId(blogId, comment.getParentId(),offset);
                if(replies.isEmpty()){
                    offset=0;
                    replies = commentService.getRepliesFromCommentId(blogId, comment.getParentId(),offset);
                }
                model.addAttribute("isReply",true);
                model.addAttribute("replies", replies.stream().filter(comment1 -> comment1.getId()!=commentId).collect(Collectors.toList()));
            }else{
                comments = commentService.getCommentsByBlogId(blogId,offset);
                if(comments.isEmpty()){
                    offset=0;
                    comments = commentService.getCommentsByBlogId(blogId,offset);
                }
                model.addAttribute("isReply",false);
            }
            model.addAttribute("comments",comments);
            model.addAttribute("offset",offset);
        }catch (Exception e){
            LOGGER.error("Error while replies of commentId : {}, exception : {}",commentId,e);
            return "user/iframe-error";
        }
        return "user/comment";
    }

    @GetMapping("/delete/{blogId}/{commentId}")
    public String deleteComment(@ModelAttribute("comment")com.s2s.scaletoscale.models.request.Comment commentReq,
                                @PathVariable("blogId") int blogId, @PathVariable("commentId") int commentId,
                                @RequestParam(value = "offset",defaultValue = "0", required = true) int offset, Model model){
        if(offset<0) offset=0;
        try {
            Comment comment = commentService.getCommentById(commentId);
            commentService.deleteComment(commentId);
            List<Comment> comments = commentService.getCommentsByBlogId(blogId,offset);
            if(comments.isEmpty()){
                offset=0;
                comments = commentService.getCommentsByBlogId(blogId,offset);
            }
            model.addAttribute("offset",offset);
            model.addAttribute("comments", comments);
            model.addAttribute("blogId", blogId);
            model.addAttribute("userId",securityUtils.getLoggedInUserId());
            model.addAttribute("isReply",false);
        }catch (Exception e){
            LOGGER.error("Error while replies of commentId : {}, exception : {}",commentId,e);
            return "user/iframe-error";
        }
        return "user/comment";
    }

    @PostMapping("")
    public String saveComment(@ModelAttribute("comment")com.s2s.scaletoscale.models.request.Comment comment,Model model){
        model.addAttribute("offset",0);
        try {
            model.addAttribute("userId",securityUtils.getLoggedInUserId());
            List<Comment> comments = commentService.saveComment(comment);
            if(isNotParentComment(comment.getParentId())){
                replies(comment.getBlogId(),comment.getParentId(),0,model);
            }else {
                model.addAttribute("comments", comments);
                model.addAttribute("blogId", comment.getBlogId());
                model.addAttribute("isReply",false);
            }
        }catch (Exception e){
            model.addAttribute("comments", new ArrayList<>());
            model.addAttribute("blogId", comment.getBlogId());
            model.addAttribute("userId",securityUtils.getLoggedInUserId());
            model.addAttribute("isReply",false);
            LOGGER.error("Error while saving comment for blogId : {}, with exception {}",comment.getBlogId(),e);
            return "user/iframe-error";
        }
        return "user/comment";
    }

    private boolean isNotParentComment(int parentId) {
        return parentId!=0 && parentId!=-1;
    }


    private void replies(int blogId, int commentId, int offset, Model model) {
        Comment comment = commentService.getCommentById(commentId);
        model.addAttribute("blogId", blogId);
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        List<Comment> replies = commentService.getRepliesFromCommentId(blogId, commentId, offset);
        if(replies.isEmpty()){
            offset=0;
            replies = commentService.getRepliesFromCommentId(blogId, commentId, offset);

        }
        model.addAttribute("offset",offset);
        model.addAttribute("replies", replies);
        model.addAttribute("comments",comments );
        model.addAttribute("replyTo",comment);
        model.addAttribute("isReply",true);
    }
}
