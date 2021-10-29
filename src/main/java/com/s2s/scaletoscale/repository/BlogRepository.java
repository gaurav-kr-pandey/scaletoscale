package com.s2s.scaletoscale.repository;

import com.s2s.scaletoscale.entities.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {
    @Query(value = "SELECT b.id, b.title, b.description, b.body, b.visibility, b.user_profile_id, b.media_url, b.post_time FROM blogs AS b " +
            "JOIN courses_blogs AS c ON b.id=c.blogs_id " +
            "WHERE c.courses_id= :courseId ORDER BY c.blogs_rank ASC",nativeQuery = true)
    List<Blog> getBlogsByCourseId(int courseId);
}
