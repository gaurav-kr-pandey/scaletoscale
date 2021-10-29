package com.s2s.scaletoscale.repository;

import com.s2s.scaletoscale.entities.Blog;
import com.s2s.scaletoscale.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {



}
