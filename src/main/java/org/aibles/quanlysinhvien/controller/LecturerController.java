package org.aibles.quanlysinhvien.controller;


import org.aibles.quanlysinhvien.constant.ResponseCode;
import org.aibles.quanlysinhvien.dto.response.BaseResponse;
import org.aibles.quanlysinhvien.dto.request.LecturerRequestDTO;
import org.aibles.quanlysinhvien.dto.response.LecturerResponseDTO;
import org.aibles.quanlysinhvien.service.LecturerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lecturers")
public class LecturerController {

    private final LecturerService lecturerService;

    public LecturerController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<LecturerResponseDTO>> create(@RequestBody LecturerRequestDTO lecturerRequestDTO) {
        LecturerResponseDTO lecturerResponseDTO = lecturerService.create(lecturerRequestDTO);
        BaseResponse<LecturerResponseDTO> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                lecturerResponseDTO
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<LecturerResponseDTO>>> getAll() {
        List<LecturerResponseDTO> lecturers = lecturerService.getAll();
        BaseResponse<List<LecturerResponseDTO>> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                lecturers
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<LecturerResponseDTO>> getById(@PathVariable int id) {
        LecturerResponseDTO lecturerResponseDTO = lecturerService.getById(id);
        BaseResponse<LecturerResponseDTO> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                lecturerResponseDTO
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<LecturerResponseDTO>> update(@PathVariable int id, @RequestBody LecturerRequestDTO lecturerRequestDTO) {
        LecturerResponseDTO lecturerResponseDTO = lecturerService.update(id, lecturerRequestDTO);
        BaseResponse<LecturerResponseDTO> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                lecturerResponseDTO
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable int id) {
        lecturerService.delete(id);
        BaseResponse<Void> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}

