package com.s2s.scaletoscale.service.impl;

import com.s2s.scaletoscale.entities.Blog;
import com.s2s.scaletoscale.models.response.Course;
import com.s2s.scaletoscale.repository.CourseRepository;
import com.s2s.scaletoscale.service.BlogService;
import com.s2s.scaletoscale.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<Course> getCourses() {
        List<Course> courseList = new ArrayList<>();
        courseRepository.findAll().forEach(course -> courseList.add(modelMapper.map(course,Course.class)));
        return courseList;
    }

    @Override
    public Course getCourseById(int id) {
        Optional<com.s2s.scaletoscale.entities.Course> course = courseRepository.findById(id);
        Course courseResponse = modelMapper.map(course.get(),Course.class);
        courseResponse.setBlogs(blogService.getBlogsByCourseId(id));
        return courseResponse;
    }

    @Override
    public void deleteCourseById(int id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Course saveCourse(com.s2s.scaletoscale.models.request.Course course) {
        com.s2s.scaletoscale.entities.Course course1 = courseRepository.save(modelMapper.map(course, com.s2s.scaletoscale.entities.Course.class));
        return modelMapper.map(course1,Course.class);
    }
}
