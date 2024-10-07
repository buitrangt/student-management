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
public class DepartmentController extends BaseController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<DepartmentResponseDTO>> create(@RequestBody DepartmentRequestDTO departmentRequestDTO) {
        DepartmentResponseDTO departmentResponseDTO = departmentService.create(departmentRequestDTO);
        return successResponse(departmentResponseDTO,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<DepartmentResponseDTO>>> getAll() {
        List<DepartmentResponseDTO> departments = departmentService.getAll();
       return successResponse(departments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<DepartmentResponseDTO>> getById(@PathVariable int id) {
        DepartmentResponseDTO departmentResponseDTO = departmentService.getById(id);
        return successResponse(departmentResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<DepartmentResponseDTO>> update(
            @PathVariable int id,
            @RequestBody DepartmentRequestDTO departmentRequestDTO
    ) {
        DepartmentResponseDTO departmentResponseDTO = departmentService.update(id, departmentRequestDTO);
        return successResponse(departmentResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable int id) {
        departmentService.delete(id);
       return successResponseNoContent();
    }
}

