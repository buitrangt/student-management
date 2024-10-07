package org.aibles.quanlysinhvien.repository;


import org.aibles.quanlysinhvien.entity.Attendance;
import org.aibles.quanlysinhvien.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {


}
