package com.s2s.scaletoscale.service;


import com.s2s.scaletoscale.models.response.Blog;
import com.s2s.scaletoscale.models.response.Course;

import java.util.List;

public interface CourseService {
    List<Course> getCourses();
    Course getCourseById(int id);
    void deleteCourseById(int id);
    Course saveCourse(com.s2s.scaletoscale.models.request.Course course);
    Course addChapter(int courseId, int blogId);
    Course removeChapter(int courseId, int blogId);
}
