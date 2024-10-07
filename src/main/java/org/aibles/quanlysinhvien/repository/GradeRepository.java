package org.aibles.quanlysinhvien.repository;


import org.aibles.quanlysinhvien.entity.Grade;
import org.aibles.quanlysinhvien.entity.GradeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade, GradeId> {
}

