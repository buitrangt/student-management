package org.aibles.quanlysinhvien.service;

import org.aibles.quanlysinhvien.dto.request.SubjectRequestDTO;
import org.aibles.quanlysinhvien.dto.response.SubjectResponseDTO;

import java.util.List;

public interface SubjectService {
    SubjectResponseDTO create(SubjectRequestDTO subjectRequestDTO);
    List<SubjectResponseDTO> getAll();
    SubjectResponseDTO getById(int subjectId);
    SubjectResponseDTO update(int subjectId, SubjectRequestDTO subjectRequestDTO);
    void delete(int subjectId);
}
