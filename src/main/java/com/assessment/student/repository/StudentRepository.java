package com.assessment.student.repository;

import com.assessment.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUsername(String username);
}
