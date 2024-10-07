package org.aibles.quanlysinhvien.repository;


import org.aibles.quanlysinhvien.entity.SubjectLecturer;
import org.aibles.quanlysinhvien.entity.SubjectLecturerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectLecturerRepository extends JpaRepository<SubjectLecturer, SubjectLecturerId> {
}

