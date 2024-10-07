package org.aibles.quanlysinhvien.controller;

import org.aibles.quanlysinhvien.constant.ResponseCode;
import org.aibles.quanlysinhvien.dto.request.SubjectLecturerRequestDTO;
import org.aibles.quanlysinhvien.dto.response.SubjectLecturerResponseDTO;
import org.aibles.quanlysinhvien.dto.response.BaseResponse;
import org.aibles.quanlysinhvien.entity.SubjectLecturerId;
import org.aibles.quanlysinhvien.service.SubjectLecturerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subjectlecturers")
public class SubjectLecturerController {

    private final SubjectLecturerService subjectLecturerService;

    public SubjectLecturerController(SubjectLecturerService subjectLecturerService) {
        this.subjectLecturerService = subjectLecturerService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<SubjectLecturerResponseDTO>> create(@RequestBody SubjectLecturerRequestDTO subjectLecturerRequestDTO) {
        SubjectLecturerResponseDTO subjectLecturerResponseDTO = subjectLecturerService.create(subjectLecturerRequestDTO);
        BaseResponse<SubjectLecturerResponseDTO> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                subjectLecturerResponseDTO
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<SubjectLecturerResponseDTO>>> getAll() {
        List<SubjectLecturerResponseDTO> subjectLecturers = subjectLecturerService.getAll();
        BaseResponse<List<SubjectLecturerResponseDTO>> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                subjectLecturers
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{subjectId}/{lecturerId}")
    public ResponseEntity<BaseResponse<SubjectLecturerResponseDTO>> getById(@PathVariable Integer subjectId, @PathVariable Integer lecturerId) {
        SubjectLecturerId id = new SubjectLecturerId(subjectId, lecturerId);
        SubjectLecturerResponseDTO subjectLecturerResponseDTO = subjectLecturerService.getById(id);
        BaseResponse<SubjectLecturerResponseDTO> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                subjectLecturerResponseDTO
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{subjectId}/{lecturerId}")
    public ResponseEntity<BaseResponse<SubjectLecturerResponseDTO>> update(
            @PathVariable Integer subjectId,
            @PathVariable Integer lecturerId,
            @RequestBody SubjectLecturerRequestDTO subjectLecturerRequestDTO
    ) {
        SubjectLecturerId id = new SubjectLecturerId(subjectId, lecturerId);
        SubjectLecturerResponseDTO subjectLecturerResponseDTO = subjectLecturerService.update(id, subjectLecturerRequestDTO);
        BaseResponse<SubjectLecturerResponseDTO> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                subjectLecturerResponseDTO
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{subjectId}/{lecturerId}")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Integer subjectId, @PathVariable Integer lecturerId) {
        SubjectLecturerId id = new SubjectLecturerId(subjectId, lecturerId);
        subjectLecturerService.delete(id);
        BaseResponse<Void> response = new BaseResponse<>(
                ResponseCode.SUCCESS,
                System.currentTimeMillis(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
