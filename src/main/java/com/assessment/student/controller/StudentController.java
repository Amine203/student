package com.assessment.student.controller;

import com.assessment.student.entity.Student;
import com.assessment.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerStudent(@RequestBody Student student) {
        studentService.registerStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body("Student registered successfully");
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authenticateStudent(@RequestBody Student student) {
        String token = studentService.authenticateStudent(student);
        if (token != null) {
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> registerStudent(@RequestParam Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.status(HttpStatus.CREATED).body("Student deleted successfully");
    }

}
