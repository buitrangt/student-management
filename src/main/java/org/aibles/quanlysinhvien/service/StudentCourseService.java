package org.aibles.quanlysinhvien.service;


import org.aibles.quanlysinhvien.dto.request.StudentCourseRequestDTO;
import org.aibles.quanlysinhvien.dto.response.StudentCourseResponseDTO;

import java.util.List;

public interface StudentCourseService {

    StudentCourseResponseDTO create(StudentCourseRequestDTO studentCourseRequestDTO);

    List<StudentCourseResponseDTO> getAll();

    StudentCourseResponseDTO getById(int studentId, int courseId);

    void delete(int studentId, int courseId);
}

