package org.aibles.quanlysinhvien.service;


import org.aibles.quanlysinhvien.dto.request.AttendanceRequestDTO;
import org.aibles.quanlysinhvien.dto.response.AttendanceResponseDTO;

import java.util.List;

public interface AttendanceService {
    AttendanceResponseDTO create(AttendanceRequestDTO attendanceRequestDTO);
    AttendanceResponseDTO update(Long attendanceId, AttendanceRequestDTO attendanceRequestDTO);
    void delete(Long attendanceId);
    AttendanceResponseDTO getById(Long attendanceId);
    List<AttendanceResponseDTO> getAll();
}

