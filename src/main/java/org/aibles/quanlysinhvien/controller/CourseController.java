package org.aibles.quanlysinhvien.controller;


import org.aibles.quanlysinhvien.constant.ResponseCode;
import org.aibles.quanlysinhvien.dto.request.CourseRequestDTO;
import org.aibles.quanlysinhvien.dto.response.CourseResponseDTO;
import org.aibles.quanlysinhvien.dto.response.BaseResponse;
import org.aibles.quanlysinhvien.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<CourseResponseDTO>> create(@RequestBody CourseRequestDTO courseRequestDTO) {
        CourseResponseDTO courseResponseDTO = courseService.create(courseRequestDTO);
        BaseResponse<CourseResponseDTO> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                courseResponseDTO
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<CourseResponseDTO>>> getAll() {
        List<CourseResponseDTO> courses = courseService.getAll();
        BaseResponse<List<CourseResponseDTO>> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                courses
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<CourseResponseDTO>> getById(@PathVariable int id) {
        CourseResponseDTO courseResponseDTO = courseService.getById(id);
        BaseResponse<CourseResponseDTO> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                courseResponseDTO
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<CourseResponseDTO>> update(
            @PathVariable int id,
            @RequestBody CourseRequestDTO courseRequestDTO
    ) {
        CourseResponseDTO courseResponseDTO = courseService.update(id, courseRequestDTO);
        BaseResponse<CourseResponseDTO> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                courseResponseDTO
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable int id) {
        courseService.delete(id);
        BaseResponse<Void> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}

