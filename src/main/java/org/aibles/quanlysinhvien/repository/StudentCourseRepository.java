package org.aibles.quanlysinhvien.repository;


import org.aibles.quanlysinhvien.entity.StudentCourse;
import org.aibles.quanlysinhvien.entity.StudentCourseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse, StudentCourseId> {
}

