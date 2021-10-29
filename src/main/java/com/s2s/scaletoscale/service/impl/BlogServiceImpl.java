package com.s2s.scaletoscale.service;

import com.s2s.scaletoscale.models.response.Blog;
import com.s2s.scaletoscale.repository.BlogRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Blog> getAllBlogs() {
        List<Blog> blogs = new ArrayList<>();
        blogRepository.findAll().forEach(blog -> modelMapper.map(blog,Blog.class));
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
        Blog blogResponse = modelMapper.map(blogRepository.save(blogEntity),Blog.class);
        return blogResponse;
    }

    @Override
    public void deleteBlog(int id) {
         blogRepository.deleteById(id);
    }

}
