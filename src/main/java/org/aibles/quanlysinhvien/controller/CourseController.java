package org.aibles.quanlysinhvien.controller;


import org.aibles.quanlysinhvien.constant.ResponseCode;
import org.aibles.quanlysinhvien.dto.request.CourseRequestDTO;
import org.aibles.quanlysinhvien.dto.response.ClassResponseDTO;
import org.aibles.quanlysinhvien.dto.response.CourseResponseDTO;
import org.aibles.quanlysinhvien.dto.response.BaseResponse;
import org.aibles.quanlysinhvien.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController extends BaseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<CourseResponseDTO>> create(@RequestBody CourseRequestDTO courseRequestDTO) {
        CourseResponseDTO courseResponseDTO = courseService.create(courseRequestDTO);
        return successResponse(courseResponseDTO, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<CourseResponseDTO>>> getAll() {
        List<CourseResponseDTO> courses = courseService.getAll();
        return successResponse(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<CourseResponseDTO>> getById(@PathVariable int id) {
        CourseResponseDTO courseResponseDTO = courseService.getById(id);
       return successResponse(courseResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<CourseResponseDTO>> update(
            @PathVariable int id,
            @RequestBody CourseRequestDTO courseRequestDTO
    ) {
        CourseResponseDTO courseResponseDTO = courseService.update(id, courseRequestDTO);
        return successResponse(courseResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable int id) {
        courseService.delete(id);
       return successResponseNoContent();
    }
}

