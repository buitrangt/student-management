package org.aibles.quanlysinhvien.service;

import org.aibles.quanlysinhvien.dto.request.ClassRequestDTO;
import org.aibles.quanlysinhvien.dto.response.ClassResponseDTO;

import java.util.List;

public interface ClassService {

    ClassResponseDTO create(ClassRequestDTO classRequestDTO);

    List<ClassResponseDTO> getAll();

    ClassResponseDTO getById(int classId);

    ClassResponseDTO update(int classId, ClassRequestDTO classRequestDTO);

    void delete(int classId);

    List<ClassResponseDTO> searchByName(String className);  // Thêm phương thức này
}



