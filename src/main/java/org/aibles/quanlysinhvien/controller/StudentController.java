package org.aibles.quanlysinhvien.controller;


import org.aibles.quanlysinhvien.constant.ResponseCode;
import org.aibles.quanlysinhvien.dto.response.BaseResponse;
import org.aibles.quanlysinhvien.dto.request.StudentRequestDTO;
import org.aibles.quanlysinhvien.dto.response.StudentResponseDTO;
import org.aibles.quanlysinhvien.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<StudentResponseDTO>> create(@RequestBody StudentRequestDTO studentRequestDTO) {
        StudentResponseDTO studentResponseDTO = studentService.create(studentRequestDTO);
        BaseResponse<StudentResponseDTO> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                studentResponseDTO
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<StudentResponseDTO>>> getAll() {
        List<StudentResponseDTO> students = studentService.getAll();
        BaseResponse<List<StudentResponseDTO>> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                students
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<StudentResponseDTO>> getById(@PathVariable int id) {
        StudentResponseDTO studentResponseDTO = studentService.getById(id);
        BaseResponse<StudentResponseDTO> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                studentResponseDTO
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<StudentResponseDTO>> update(@PathVariable int id, @RequestBody StudentRequestDTO studentRequestDTO) {
        StudentResponseDTO studentResponseDTO = studentService.update(id, studentRequestDTO);
        BaseResponse<StudentResponseDTO> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                studentResponseDTO
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable int id) {
        studentService.delete(id);
        BaseResponse<Void> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}

