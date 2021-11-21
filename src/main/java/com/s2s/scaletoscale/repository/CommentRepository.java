package com.s2s.scaletoscale.repository;


import com.s2s.scaletoscale.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query(value = "SELECT * FROM comments WHERE blogs_id= :blogId AND parent_id= -1 ORDER BY id DESC LIMIT :offset,10", nativeQuery = true)
    List<Comment> getCommentsByBlogId(int blogId, int offset);

    @Query(value = "SELECT * FROM comments WHERE blogs_id= :blogId AND parent_id= :parentId ORDER BY id DESC LIMIT :offset,10", nativeQuery = true)
    List<Comment> getRepliesFromCommentId(int blogId, int parentId, int offset);

    void deleteByParentId(int parentId);
}
