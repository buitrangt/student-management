package org.aibles.quanlysinhvien.repository;


import org.aibles.quanlysinhvien.entity.Course;
import org.aibles.quanlysinhvien.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByStudentId(Long courseId);
}

