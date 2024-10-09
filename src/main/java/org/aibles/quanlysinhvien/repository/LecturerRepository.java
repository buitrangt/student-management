package org.aibles.quanlysinhvien.repository;


import org.aibles.quanlysinhvien.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Integer> {
}
