package com.cts.dao;

import org.springframework.data.repository.CrudRepository;


import com.cts.model.Course;
/**
 * CourseDao Interface
 *
 */
public interface CourseDao extends CrudRepository<Course, Long>
{


}