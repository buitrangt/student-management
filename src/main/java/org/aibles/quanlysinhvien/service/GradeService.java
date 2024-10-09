package org.aibles.quanlysinhvien.service;


import org.aibles.quanlysinhvien.dto.request.GradeRequestDTO;
import org.aibles.quanlysinhvien.dto.response.GradeResponseDTO;

import java.util.List;

public interface GradeService {

    GradeResponseDTO create(GradeRequestDTO gradeRequestDTO);

    List<GradeResponseDTO> getAll();

    GradeResponseDTO getById(Integer studentId, Integer courseId);

    GradeResponseDTO update(Integer studentId, Integer courseId, GradeRequestDTO gradeRequestDTO);

    void delete(Integer studentId, Integer courseId);
}

