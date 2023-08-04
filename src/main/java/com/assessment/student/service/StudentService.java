package com.assessment.student.service;

import com.assessment.student.entity.Course;
import com.assessment.student.entity.Student;
import com.assessment.student.entity.StudentCourse;
import com.assessment.student.exception.CourseNotFoundException;
import com.assessment.student.repository.StudentCourseRepository;
import com.assessment.student.repository.StudentRepository;
import com.assessment.student.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final StudentCourseRepository studentCourseRepository;
    private final CourseClient courseClient;



    public void registerStudent(Student student) {
        String encodedPassword = passwordEncoder.encode(student.getPassword());
        student.setPassword(encodedPassword);
        studentRepository.save(student);
    }

    public void deleteStudent(Long id){
        studentRepository.deleteById(id);
    }

    public String authenticateStudent(Student student) {
        Student storedStudent = studentRepository.findByUsername(student.getUsername());
        if (storedStudent != null && passwordEncoder.matches(student.getPassword(), storedStudent.getPassword())) {
            return jwtTokenProvider.generateToken(student.getUsername());
        } else {
            return null;
        }
    }

    public void allocateStudentCourse(String user , Long courseId ){
        Student student = studentRepository.findByUsername(user);
        Course course=courseClient.findCourseById(courseId);
         if (course== null){
           throw  new CourseNotFoundException(courseId);
        }else{
             StudentCourse studentCourse = new StudentCourse();
             studentCourse.setCourseId(courseId);
             studentCourse.setStudent(student);
             studentCourseRepository.save(studentCourse);
         }
    }
    public List<Student> getStudentByCourseId(Long courseId){
        List<StudentCourse>  studentCourses = studentCourseRepository.findStudentByCourseId(courseId);
        List<Student> student = studentCourses.stream().map(studentCourse -> studentCourse.getStudent()).collect(Collectors.toList());
        return student;
    }
}
