package com.s2s.scaletoscale.service;

import com.s2s.scaletoscale.models.response.Blog;
import java.util.List;
import java.util.Optional;

public interface BlogService {
    List<Blog> getAllBlogs();
    Optional<Blog> getBlog(int id);
    Blog saveBlog(com.s2s.scaletoscale.models.request.Blog blog);
    void deleteBlog(int id);
    Blog toggleBlogVisibility(int id);
    List<Blog> getBlogsByCourseId(int id);
}
