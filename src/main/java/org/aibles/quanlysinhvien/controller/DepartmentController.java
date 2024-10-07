package org.aibles.quanlysinhvien.controller;


import org.aibles.quanlysinhvien.constant.ResponseCode;
import org.aibles.quanlysinhvien.dto.response.BaseResponse;
import org.aibles.quanlysinhvien.dto.request.DepartmentRequestDTO;
import org.aibles.quanlysinhvien.dto.response.DepartmentResponseDTO;
import org.aibles.quanlysinhvien.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<DepartmentResponseDTO>> create(@RequestBody DepartmentRequestDTO departmentRequestDTO) {
        DepartmentResponseDTO departmentResponseDTO = departmentService.create(departmentRequestDTO);
        BaseResponse<DepartmentResponseDTO> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                departmentResponseDTO
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<DepartmentResponseDTO>>> getAll() {
        List<DepartmentResponseDTO> departments = departmentService.getAll();
        BaseResponse<List<DepartmentResponseDTO>> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                departments
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<DepartmentResponseDTO>> getById(@PathVariable int id) {
        DepartmentResponseDTO departmentResponseDTO = departmentService.getById(id);
        BaseResponse<DepartmentResponseDTO> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                departmentResponseDTO
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<DepartmentResponseDTO>> update(
            @PathVariable int id,
            @RequestBody DepartmentRequestDTO departmentRequestDTO
    ) {
        DepartmentResponseDTO departmentResponseDTO = departmentService.update(id, departmentRequestDTO);
        BaseResponse<DepartmentResponseDTO> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                departmentResponseDTO
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable int id) {
        departmentService.delete(id);
        BaseResponse<Void> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}

