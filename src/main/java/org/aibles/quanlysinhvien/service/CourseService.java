package org.aibles.quanlysinhvien.service;


import org.aibles.quanlysinhvien.dto.response.CourseResponseDTO;
import org.aibles.quanlysinhvien.dto.request.CourseRequestDTO;

import java.util.List;

public interface CourseService {

    CourseResponseDTO create(CourseRequestDTO courseRequestDTO);

    List<CourseResponseDTO> getAll();

    CourseResponseDTO getById(int courseId);

    CourseResponseDTO update(int courseId, CourseRequestDTO courseRequestDTO);

    void delete(int courseId);
}

