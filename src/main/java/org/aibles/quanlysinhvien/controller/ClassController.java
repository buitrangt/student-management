package org.aibles.quanlysinhvien.controller;

import org.aibles.quanlysinhvien.constant.ResponseCode;
import org.aibles.quanlysinhvien.dto.request.ClassRequestDTO;
import org.aibles.quanlysinhvien.dto.response.ClassResponseDTO;
import org.aibles.quanlysinhvien.dto.response.BaseResponse;
import org.aibles.quanlysinhvien.service.ClassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/classes")
public class ClassController {

    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<ClassResponseDTO>> create(@RequestBody ClassRequestDTO classRequestDTO) {
        ClassResponseDTO classResponseDTO = classService.create(classRequestDTO);
        BaseResponse<ClassResponseDTO> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                classResponseDTO
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<ClassResponseDTO>>> getAll() {
        List<ClassResponseDTO> classes = classService.getAll();
        BaseResponse<List<ClassResponseDTO>> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                classes
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ClassResponseDTO>> getById(@PathVariable int id) {
        ClassResponseDTO classResponseDTO = classService.getById(id);
        BaseResponse<ClassResponseDTO> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                classResponseDTO
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<ClassResponseDTO>> update(
            @PathVariable int id,
            @RequestBody ClassRequestDTO classRequestDTO
    ) {
        ClassResponseDTO classResponseDTO = classService.update(id, classRequestDTO);
        BaseResponse<ClassResponseDTO> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                classResponseDTO
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable int id) {
        classService.delete(id);
        BaseResponse<Void> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<List<ClassResponseDTO>>> search(@RequestParam String className) {
        List<ClassResponseDTO> classes = classService.searchByName(className);
        BaseResponse<List<ClassResponseDTO>> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                classes
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
