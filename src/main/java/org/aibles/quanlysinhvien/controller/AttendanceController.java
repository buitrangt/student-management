package org.aibles.quanlysinhvien.controller;


import lombok.extern.slf4j.Slf4j;
import org.aibles.quanlysinhvien.dto.request.AttendanceRequestDTO;
import org.aibles.quanlysinhvien.dto.response.AttendanceListResponse;
import org.aibles.quanlysinhvien.dto.response.AttendanceResponseDTO;
import org.aibles.quanlysinhvien.dto.response.BaseResponse;
import org.aibles.quanlysinhvien.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceController extends BaseController {

    private final AttendanceService attendanceService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AttendanceRequestDTO attendanceRequestDTO) {
        log.info("(create) - attendanceRequestDTO: {}", attendanceRequestDTO);
        AttendanceResponseDTO responseDTO = attendanceService.create(attendanceRequestDTO);
        return successResponse(responseDTO);
    }

    @PutMapping("/{attendanceId}")
    public ResponseEntity<?> update(
            @PathVariable Long attendanceId,
            @RequestBody AttendanceRequestDTO attendanceRequestDTO) {
        log.info("(update) - attendanceId: {}, attendanceRequestDTO: {}", attendanceId, attendanceRequestDTO);
        AttendanceResponseDTO responseDTO = attendanceService.update(attendanceId, attendanceRequestDTO);
        return successResponse(responseDTO);
    }

    @DeleteMapping("/{attendanceId}")
    public ResponseEntity<?> delete(@PathVariable Long attendanceId) {
        log.info("(delete) - attendanceId: {}", attendanceId);
        attendanceService.delete(attendanceId);
        return successResponseNoContent();
    }

    @GetMapping("/{attendanceId}")
    public ResponseEntity<?> getById(@PathVariable Long attendanceId) {
        log.info("(getById) - attendanceId: {}", attendanceId);
        AttendanceResponseDTO responseDTO = attendanceService.getById(attendanceId);
        return successResponse(responseDTO);
    }

    @GetMapping("/attendances")
    public ResponseEntity<BaseResponse<AttendanceListResponse>> getAll() {
        log.info("(getAll) - retrieving all attendance records");

        List<AttendanceResponseDTO> responseDTOList = attendanceService.getAll();

        AttendanceListResponse attendanceListResponse = new AttendanceListResponse(responseDTOList);

        BaseResponse<AttendanceListResponse> response = BaseResponse.success(attendanceListResponse);

        return ResponseEntity.ok(response);
    }


}

