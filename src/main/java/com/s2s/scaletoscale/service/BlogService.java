package com.s2s.scaletoscale.service;

import com.s2s.scaletoscale.models.response.Blog;

import java.awt.print.Pageable;
import java.util.List;

public interface BlogService {
    List<Blog> getAllBlogs();
    List<Blog> getAllBlogsByTags(com.s2s.scaletoscale.models.request.Blog.Tag tag, Pageable pageable);
}
