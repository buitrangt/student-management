package org.aibles.quanlysinhvien.controller;


import org.aibles.quanlysinhvien.constant.ResponseCode;
import org.aibles.quanlysinhvien.dto.response.BaseResponse;
import org.aibles.quanlysinhvien.dto.request.StudentRequestDTO;
import org.aibles.quanlysinhvien.dto.response.StudentListResponse;
import org.aibles.quanlysinhvien.dto.response.StudentResponseDTO;
import org.aibles.quanlysinhvien.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController extends BaseController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<StudentResponseDTO>> create(@RequestBody StudentRequestDTO studentRequestDTO) {
        StudentResponseDTO studentResponseDTO = studentService.create(studentRequestDTO);
        return successResponse(studentResponseDTO,HttpStatus.CREATED);
    }
    @GetMapping("/students")
    public ResponseEntity<BaseResponse<StudentListResponse>> getAllStudents() {
        List<StudentResponseDTO> students = studentService.getAll();

        StudentListResponse studentListResponse = new StudentListResponse(students, students.size());

        BaseResponse<StudentListResponse> response = BaseResponse.success(studentListResponse);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<StudentResponseDTO>> getById(@PathVariable int id) {
        StudentResponseDTO studentResponseDTO = studentService.getById(id);
       return successResponse(studentResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<StudentResponseDTO>> update(@PathVariable int id, @RequestBody StudentRequestDTO studentRequestDTO) {
        StudentResponseDTO studentResponseDTO = studentService.update(id, studentRequestDTO);
       return successResponse(studentResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable int id) {
        studentService.delete(id);
       return successResponseNoContent();
    }
}

