package org.aibles.quanlysinhvien.service;


import org.aibles.quanlysinhvien.dto.request.StudentRequestDTO;
import org.aibles.quanlysinhvien.dto.response.StudentResponseDTO;

import java.util.List;

public interface StudentService {

    StudentResponseDTO create(StudentRequestDTO studentRequestDTO);

    List<StudentResponseDTO> getAll();

    StudentResponseDTO getById(int studentId);

    StudentResponseDTO update(int studentId, StudentRequestDTO studentRequestDTO);

    void delete(int studentId);
}

