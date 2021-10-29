package com.s2s.scaletoscale.service.impl;

import com.s2s.scaletoscale.entities.UserProfile;
import com.s2s.scaletoscale.models.response.Blog;
import com.s2s.scaletoscale.models.response.Course;
import com.s2s.scaletoscale.repository.BlogRepository;
import com.s2s.scaletoscale.service.BlogService;
import com.s2s.scaletoscale.service.UserProfileService;
import com.s2s.scaletoscale.utils.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private ModelMapper modelMapper;


    @PostConstruct
    void init(){
        System.out.println(getBlogsByCourseId(1));
    }

    @Override
    public List<Blog> getAllBlogs() {
        List<Blog> blogs = new ArrayList<>();
        blogRepository.findAll().forEach(blog -> blogs.add(modelMapper.map(blog,Blog.class)));
        return blogs;
    }

    @Override
    public Optional<Blog> getBlog(int id) {
        Optional<com.s2s.scaletoscale.entities.Blog> blog = blogRepository.findById(id);
        if(blog.isPresent()) {
            Blog blogResponse = modelMapper.map(blog.get(), Blog.class);
            return Optional.of(blogResponse);
        }
        return Optional.empty();
    }

    @Override
    public Blog saveBlog(com.s2s.scaletoscale.models.request.Blog blogRequest) {
        com.s2s.scaletoscale.entities.Blog blogEntity = modelMapper.map(blogRequest, com.s2s.scaletoscale.entities.Blog.class);
        UserProfile userProfile = modelMapper.map(userProfileService.getUserProfile(securityUtils.getLoggedInUsername()),UserProfile.class);
        blogEntity.setUserProfile(userProfile);
        blogEntity.setPost_time(System.currentTimeMillis());
        Blog blogResponse = modelMapper.map(blogRepository.save(blogEntity),Blog.class);
        return blogResponse;
    }

    @Override
    public void deleteBlog(int id) {
         blogRepository.deleteById(id);
    }

    @Override
    public Blog toggleBlogVisibility(int id) {
        com.s2s.scaletoscale.entities.Blog blog = blogRepository.getById(id);
        Byte b = blog.getVisibility();
        blog.setVisibility(b.intValue()==1 ? Byte.valueOf("0") : Byte.valueOf("1"));
        blogRepository.save(blog);
        return modelMapper.map(blog,Blog.class);
    }

    @Override
    public List<Blog> getBlogsByCourseId(int id) {
        List<com.s2s.scaletoscale.models.response.Blog> blogs = new ArrayList<>();
        blogRepository.getBlogsByCourseId(id).forEach(blog -> {
            blogs.add(modelMapper.map(blog, com.s2s.scaletoscale.models.response.Blog.class));
        });
        return blogs;
    }

}
