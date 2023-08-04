package com.assessment.student.repository;

import com.assessment.student.entity.Student;
import com.assessment.student.entity.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {
    List<StudentCourse> findByStudent(Student student);

    List<StudentCourse> findStudentByCourseId(Long courseId);
}
