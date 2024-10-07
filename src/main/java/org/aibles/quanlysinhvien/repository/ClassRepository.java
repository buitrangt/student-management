package org.aibles.quanlysinhvien.repository;
import org.aibles.quanlysinhvien.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.aibles.quanlysinhvien.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Integer> {

    // Tìm kiếm lớp học theo tên (không phân biệt chữ hoa chữ thường)
    List<ClassEntity> findByClassNameContainingIgnoreCase(String className);
}


