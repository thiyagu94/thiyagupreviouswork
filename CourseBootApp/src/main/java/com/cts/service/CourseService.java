package com.cts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.dao.CourseDao;
import com.cts.model.Course;

@Service
public class CourseService
{
	@Autowired 
	private CourseDao courseDao;
	
	/**
	 * Get a list of courses
	 *
	 */
	public List<Course> getCourses() throws Exception 
	{
		
		return (List<Course>) courseDao.findAll();
	}
	
	/**
	 * Save a course
	 *
	 */
	public Course saveCourse(Course course) throws Exception
	{
		return courseDao.save(course);
	}
	
	/**
	 * Get Course details
	 *
	 */
	public Course getCourseDetails(Long id) throws Exception
	{
		Course course = courseDao.findOne(id);
		return course;
	}
	
	/**
	 * Update a course
	 *
	 */
	public Course updateCourse(Course course) throws Exception
	{
		
		return courseDao.save(course);
	}

	/**
	 * Delete a course
	 *
	 */
	public void deleteCourse(Long id) throws Exception
	{
		courseDao.delete(id);
	}
	
}