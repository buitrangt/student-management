package org.aibles.quanlysinhvien.service;


import org.aibles.quanlysinhvien.dto.request.LecturerRequestDTO;
import org.aibles.quanlysinhvien.dto.response.LecturerResponseDTO;

import java.util.List;

public interface LecturerService {

    LecturerResponseDTO create(LecturerRequestDTO lecturerRequestDTO);

    List<LecturerResponseDTO> getAll();

    LecturerResponseDTO getById(int lecturerId);

    LecturerResponseDTO update(int lecturerId, LecturerRequestDTO lecturerRequestDTO);

    void delete(int lecturerId);
}

