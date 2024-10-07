package org.aibles.quanlysinhvien.controller;

import org.aibles.quanlysinhvien.constant.ResponseCode;
import org.aibles.quanlysinhvien.dto.request.SubjectRequestDTO;
import org.aibles.quanlysinhvien.dto.response.SubjectResponseDTO;
import org.aibles.quanlysinhvien.dto.response.BaseResponse;
import org.aibles.quanlysinhvien.service.SubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<SubjectResponseDTO>> create(@RequestBody SubjectRequestDTO subjectRequestDTO) {
        SubjectResponseDTO subjectResponseDTO = subjectService.create(subjectRequestDTO);
        BaseResponse<SubjectResponseDTO> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                subjectResponseDTO
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<SubjectResponseDTO>>> getAll() {
        List<SubjectResponseDTO> subjects = subjectService.getAll();
        BaseResponse<List<SubjectResponseDTO>> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                subjects
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<SubjectResponseDTO>> getById(@PathVariable int id) {
        SubjectResponseDTO subjectResponseDTO = subjectService.getById(id);
        BaseResponse<SubjectResponseDTO> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                subjectResponseDTO
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<SubjectResponseDTO>> update(
            @PathVariable int id,
            @RequestBody SubjectRequestDTO subjectRequestDTO
    ) {
        SubjectResponseDTO subjectResponseDTO = subjectService.update(id, subjectRequestDTO);
        BaseResponse<SubjectResponseDTO> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                subjectResponseDTO
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable int id) {
        subjectService.delete(id);
        BaseResponse<Void> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
