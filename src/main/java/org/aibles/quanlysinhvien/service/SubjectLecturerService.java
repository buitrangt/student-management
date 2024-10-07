package org.aibles.quanlysinhvien.service;

import org.aibles.quanlysinhvien.dto.request.SubjectLecturerRequestDTO;
import org.aibles.quanlysinhvien.dto.response.SubjectLecturerResponseDTO;
import org.aibles.quanlysinhvien.entity.SubjectLecturerId;

import java.util.List;

public interface SubjectLecturerService {
    SubjectLecturerResponseDTO create(SubjectLecturerRequestDTO subjectLecturerRequestDTO);
    List<SubjectLecturerResponseDTO> getAll();
    SubjectLecturerResponseDTO getById(SubjectLecturerId id);
    SubjectLecturerResponseDTO update(SubjectLecturerId id, SubjectLecturerRequestDTO subjectLecturerRequestDTO);
    void delete(SubjectLecturerId id);
}
