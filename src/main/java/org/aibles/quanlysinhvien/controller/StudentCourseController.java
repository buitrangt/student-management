package org.aibles.quanlysinhvien.controller;


import org.aibles.quanlysinhvien.constant.ResponseCode;
import org.aibles.quanlysinhvien.dto.response.BaseResponse;
import org.aibles.quanlysinhvien.dto.request.StudentCourseRequestDTO;
import org.aibles.quanlysinhvien.dto.response.StudentCourseResponseDTO;
import org.aibles.quanlysinhvien.service.StudentCourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/studentcourses")
public class StudentCourseController {

    private final StudentCourseService studentCourseService;

    public StudentCourseController(StudentCourseService studentCourseService) {
        this.studentCourseService = studentCourseService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<StudentCourseResponseDTO>> create(@RequestBody StudentCourseRequestDTO studentCourseRequestDTO) {
        StudentCourseResponseDTO studentCourseResponseDTO = studentCourseService.create(studentCourseRequestDTO);
        BaseResponse<StudentCourseResponseDTO> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                studentCourseResponseDTO
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<StudentCourseResponseDTO>>> getAll() {
        List<StudentCourseResponseDTO> studentCourses = studentCourseService.getAll();
        BaseResponse<List<StudentCourseResponseDTO>> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                studentCourses
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/students/{studentId}/courses/{courseId}")
    public ResponseEntity<BaseResponse<StudentCourseResponseDTO>> getById(
            @PathVariable int studentId,
            @PathVariable int courseId
    ) {
        StudentCourseResponseDTO studentCourseResponseDTO = studentCourseService.getById(studentId, courseId);
        BaseResponse<StudentCourseResponseDTO> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                studentCourseResponseDTO
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/students/{studentId}/courses/{courseId}")
    public ResponseEntity<BaseResponse<Void>> delete(
            @PathVariable int studentId,
            @PathVariable int courseId
    ) {
        studentCourseService.delete(studentId, courseId);
        BaseResponse<Void> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}

