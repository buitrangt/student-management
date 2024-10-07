package org.aibles.quanlysinhvien.service;


import org.aibles.quanlysinhvien.dto.request.DepartmentRequestDTO;
import org.aibles.quanlysinhvien.dto.response.DepartmentResponseDTO;

import java.util.List;

public interface DepartmentService {

    DepartmentResponseDTO create(DepartmentRequestDTO departmentRequestDTO);

    List<DepartmentResponseDTO> getAll();

    DepartmentResponseDTO getById(int departmentId);

    DepartmentResponseDTO update(int departmentId, DepartmentRequestDTO departmentRequestDTO);

    void delete(int departmentId);
}

