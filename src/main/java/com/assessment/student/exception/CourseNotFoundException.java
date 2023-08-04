package com.assessment.student.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(Long courseId) {
        super("Course not found with ID: " + courseId);
    }
}

