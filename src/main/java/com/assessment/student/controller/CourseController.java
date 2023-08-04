package com.assessment.student.controller;

import com.assessment.student.entity.Course;
import com.assessment.student.entity.Student;
import com.assessment.student.entity.StudentCourse;
import com.assessment.student.model.SelectedCourse;
import com.assessment.student.service.CourseClient;
import com.assessment.student.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CourseController {

    private final CourseClient courseClient;
    private final StudentService studentService;



    @GetMapping("/available-courses")
    public List<Course> getAvailableCourses() {
        return courseClient.getAllCourses();
    }

    @PostMapping("/allocateCourse")
    public String getAvailableCourses(@RequestBody SelectedCourse selectedCourse , Authentication user) {
        studentService.allocateStudentCourse(user.getPrincipal().toString(),selectedCourse.getId());
        return "ok";
    }

    @GetMapping("/studentByCourse")
    public List<Student> getStudentByCourseId(@RequestParam Long id) {
       return studentService.getStudentByCourseId(id);
    }
}
