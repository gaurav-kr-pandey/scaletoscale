package com.s2s.scaletoscale.service.impl;

import com.s2s.scaletoscale.models.response.Blog;
import com.s2s.scaletoscale.models.response.Comment;
import com.s2s.scaletoscale.models.response.UserProfile;
import com.s2s.scaletoscale.repository.CommentRepository;
import com.s2s.scaletoscale.service.BlogService;
import com.s2s.scaletoscale.service.CommentService;
import com.s2s.scaletoscale.service.UserProfileService;
import com.s2s.scaletoscale.utils.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> getCommentsByBlogId(int blogId, int offset) {
        List<Comment> comments = new ArrayList<>();
        List<com.s2s.scaletoscale.entities.Comment> commentList = commentRepository.getCommentsByBlogId(blogId,offset);
        commentList.forEach(comment->comments.add(modelMapper.map(comment,Comment.class)));
        return comments;
    }

    @Override
    public List<Comment> saveComment(com.s2s.scaletoscale.models.request.Comment commentReq) {
        Blog blog = blogService.getBlog(commentReq.getBlogId()).get();
        UserProfile userProfile = userProfileService.getUserProfile(securityUtils.getLoggedInUsername());
        com.s2s.scaletoscale.entities.Comment comment = modelMapper.map(commentReq, com.s2s.scaletoscale.entities.Comment.class);
        comment.setUserProfile(modelMapper.map(userProfile, com.s2s.scaletoscale.entities.UserProfile.class));
        comment.setBlog(modelMapper.map(blog, com.s2s.scaletoscale.entities.Blog.class));
        if(comment.getParentId()==0)
            comment.setParentId(-1);
        commentRepository.save(comment);
        List<Comment> comments = new ArrayList<>();
        Pageable pageable =  PageRequest.of(0, 5);
        commentRepository.getCommentsByBlogId(commentReq.getBlogId(),0).forEach(comment1 -> comments.add(modelMapper.map(comment1,Comment.class)));
        return comments;
    }

    @Transactional
    @Override
    public void deleteComment(int id) {
        commentRepository.deleteByParentId(id);
        commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> getRepliesFromCommentId(int blogId, int commentId, int offset) {
        List<Comment> replies = new ArrayList<>();
        commentRepository.getRepliesFromCommentId(blogId,commentId,offset).forEach(comment->replies.add(modelMapper.map(comment,Comment.class)));
        return replies;
    }

    @Override
    public Comment getCommentById(int id) {
        return modelMapper.map(commentRepository.getById(id),Comment.class);
    }

}
