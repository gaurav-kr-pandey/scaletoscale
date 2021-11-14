package com.s2s.scaletoscale.service;

import com.s2s.scaletoscale.models.request.Comment;

import java.util.List;

public interface CommentService {
    List<com.s2s.scaletoscale.models.response.Comment> getCommentsByBlogId(int id, int offset);
    List<com.s2s.scaletoscale.models.response.Comment> saveComment(Comment comment);
    void deleteComment(int id);
    List<com.s2s.scaletoscale.models.response.Comment> getRepliesFromCommentId(int blogId, int commentId, int offset);
    com.s2s.scaletoscale.models.response.Comment getCommentById(int id);
}
