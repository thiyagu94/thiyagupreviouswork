package com.cts.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cts.exception.AppException;
import com.cts.helper.CourseConstant;
import com.cts.helper.CourseResponse;
import com.cts.model.Course;
import com.cts.service.CourseService;

@RestController
@RequestMapping("/api")
public class CourseController {
	
	@Autowired 
	CourseService courseService;
	
	/**
	 *  Logger
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(CourseController.class);
	
	CourseResponse response = new CourseResponse();
	
	/**
	 * Lists the courses
	 * @return List of courses
	 * @throws Exception
	 */
	@RequestMapping(value="/courses", method = RequestMethod.GET)
	public List<Course> list() throws Exception
	{
		List<Course> courseList = new ArrayList<Course>();
		try
		{	
			logger.debug("List of courses ");
			// Get the list of courses
			courseList = courseService.getCourses();
		}
		catch(Exception e)
		{
			throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString());
		}
		return courseList;
	}
	
	/**
	 * Get the details of a course
	 * @param id
	 * @return Course
	 * @throws Exception
	 */
	@RequestMapping(value="/courses/{id}", method = RequestMethod.GET)
	public ResponseEntity<Course> get(@PathVariable Long id) throws Exception
	{
		Course course = new Course();
		logger.debug("Course detail of course with id "+id);
		// Get the existing course details
		course = courseService.getCourseDetails(id);
		if(course == null)
		{
			// Throw exception if the course does not exist
			throw new AppException(HttpStatus.NOT_FOUND.value(),CourseConstant.COURSE_NOT_FOUND);
		}
		return new ResponseEntity<Course>(course, HttpStatus.OK);
	}
	
	/**
	 * Create a course
	 * @param course
	 * @return CourseResponse
	 * @throws Exception
	 */
	@RequestMapping(value="/course", method = RequestMethod.POST)
	public CourseResponse createCourse(@RequestBody Course course) throws Exception
	{
		if(course == null || course.getCourseName() == null || course.getDuration() == null)
		{
			// Throw exception if the input is null
			throw new AppException(HttpStatus.BAD_REQUEST.value(),CourseConstant.INVALID_REQUEST); 
		}
		logger.debug("Save Course with name "+course.getCourseName());
		//Save the course
		courseService.saveCourse(course);
		//Set the success message
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setStatusMessage(CourseConstant.CREATE_SUCCESS);
		logger.debug("Course created successfuly with name "+course.getCourseName());
		return response;
	}
	
	/**
	 * Update a course
	 * @param id
	 * @param course
	 * @return CourseResponse
	 * @throws Exception
	 */
	@RequestMapping(value="/courses/{id}", method = RequestMethod.PUT)
	public CourseResponse updateCourse(@PathVariable Long id, @RequestBody Course course) throws Exception
	{
		// Get the existing course details
		Course existingCourse = courseService.getCourseDetails(id);
		logger.debug("Update Course with id "+id);
		if(course == null || course.getCourseId() == null || course.getDuration() == null)
		{
			throw new AppException(HttpStatus.BAD_REQUEST.value(),CourseConstant.INVALID_REQUEST); 
		}

		if(existingCourse == null)
		{
			// Throw exception if the course does not exist
			throw new AppException(HttpStatus.NOT_FOUND.value(),CourseConstant.COURSE_NOT_FOUND);
		}
		else
		{
			//Update the course
			courseService.updateCourse(course);
			//Set the success message
			response.setStatusCode(HttpStatus.OK.value());
			response.setStatusMessage(CourseConstant.UPDATE_SUCCESS);
			logger.debug("Course updated successfuly with id "+course.getCourseId());
		}
		return response;
	}
	
	/**
	 * Delete a course
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/courses/{id}", method = RequestMethod.DELETE)
	public CourseResponse deleteCourse(@PathVariable Long id) throws Exception
	{
		logger.debug("Delete Course with id "+id);
		Course existingCourse;
		// Get the existing course details
		existingCourse = courseService.getCourseDetails(id);
		if(existingCourse == null)
		{
			// Throw exception if the course does not exist
			throw new AppException(HttpStatus.NOT_FOUND.value(),CourseConstant.COURSE_NOT_FOUND);
		}
		else
		{
			// Delete the course
			courseService.deleteCourse(id);
			//Set the success message
			response.setStatusCode(HttpStatus.OK.value());
			response.setStatusMessage(CourseConstant.DELETE_SUCCESS);
			logger.debug("Course updated successfuly with id "+id);
		}

		return response;
	}
	
	/**
	 * Handles exceptions with http error codes
	 * @param e
	 * @param response
	 * @throws IOException
	 */
	@ExceptionHandler
	void AppException(AppException e, HttpServletResponse response) throws Exception 
	{
		switch (e.getStatus()) 
		{
		case 400 :
			response.sendError(HttpStatus.BAD_REQUEST.value(), CourseConstant.VALID_INPUT);
			break;

		case 404 : 
			response.sendError(HttpStatus.NOT_FOUND.value(), CourseConstant.COURSE_NOT_FOUND);
			break;

		case 500 : 
			response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString());
			break;
		}
	}
	
}