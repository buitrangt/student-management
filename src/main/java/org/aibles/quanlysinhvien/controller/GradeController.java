package org.aibles.quanlysinhvien.controller;


import org.aibles.quanlysinhvien.constant.ResponseCode;
import org.aibles.quanlysinhvien.dto.response.BaseResponse;
import org.aibles.quanlysinhvien.dto.request.GradeRequestDTO;
import org.aibles.quanlysinhvien.dto.response.GradeResponseDTO;
import org.aibles.quanlysinhvien.service.GradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/grades")
public class GradeController extends BaseController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<GradeResponseDTO>> create(@RequestBody GradeRequestDTO gradeRequestDTO) {
        GradeResponseDTO gradeResponseDTO = gradeService.create(gradeRequestDTO);
        return successResponse(gradeResponseDTO,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<GradeResponseDTO>>> getAll() {
        List<GradeResponseDTO> grades = gradeService.getAll();
        return successResponse(grades);
    }

    @GetMapping("/students/{studentId}/courses/{courseId}")
    public ResponseEntity<BaseResponse<GradeResponseDTO>> getById(
            @PathVariable Integer studentId,
            @PathVariable Integer courseId
    ) {
        GradeResponseDTO gradeResponseDTO = gradeService.getById(studentId, courseId);
       return successResponse(gradeResponseDTO);
    }

    @PutMapping("/students/{studentId}/courses/{courseId}")
    public ResponseEntity<BaseResponse<GradeResponseDTO>> update(
            @PathVariable Integer studentId,
            @PathVariable Integer courseId,
            @RequestBody GradeRequestDTO gradeRequestDTO
    ) {
        GradeResponseDTO gradeResponseDTO = gradeService.update(studentId, courseId, gradeRequestDTO);
        return successResponse(gradeResponseDTO);
    }

    @DeleteMapping("/students/{studentId}/courses/{courseId}")
    public ResponseEntity<BaseResponse<Void>> delete(
            @PathVariable Integer studentId,
            @PathVariable Integer courseId
    ) {
        gradeService.delete(studentId, courseId);
       return successResponseNoContent();
    }
}

