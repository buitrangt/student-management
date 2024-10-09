package org.aibles.quanlysinhvien.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.aibles.quanlysinhvien.dto.request.SubjectLecturerRequestDTO;
import org.aibles.quanlysinhvien.dto.response.SubjectLecturerResponseDTO;
import org.aibles.quanlysinhvien.entity.SubjectLecturer;
import org.aibles.quanlysinhvien.entity.SubjectLecturerId;
import org.aibles.quanlysinhvien.exception.BusinessException;
import org.aibles.quanlysinhvien.exception.InstructorCode;
import org.aibles.quanlysinhvien.repository.SubjectLecturerRepository;
import org.aibles.quanlysinhvien.service.SubjectLecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SubjectLecturerServiceImpl implements SubjectLecturerService {

    private final SubjectLecturerRepository subjectLecturerRepository;

    @Autowired
    public SubjectLecturerServiceImpl(SubjectLecturerRepository subjectLecturerRepository) {
        this.subjectLecturerRepository = subjectLecturerRepository;
    }

    @Override
    @Transactional
    public SubjectLecturerResponseDTO create(SubjectLecturerRequestDTO subjectLecturerRequestDTO) {
        validateSubjectLecturerRequestDTO(subjectLecturerRequestDTO);
        log.info("(createSubjectLecturer) Start - subjectLecturerRequestDTO: {}", subjectLecturerRequestDTO);

        SubjectLecturer subjectLecturer = new SubjectLecturer();
        subjectLecturer.setSubjectId(subjectLecturerRequestDTO.getSubjectId());
        subjectLecturer.setLecturerId(subjectLecturerRequestDTO.getLecturerId());

        SubjectLecturer savedSubjectLecturer = subjectLecturerRepository.save(subjectLecturer);
        log.info("(createSubjectLecturer) Successfully created subject lecturer - subjectId: {}, lecturerId: {}",
                savedSubjectLecturer.getSubjectId(), savedSubjectLecturer.getLecturerId());

        return mapToSubjectLecturerResponseDTO(savedSubjectLecturer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectLecturerResponseDTO> getAll() {
        log.info("(getAllSubjectLecturers) Start - retrieving all subject lecturers");
        List<SubjectLecturer> subjectLecturers = subjectLecturerRepository.findAll();
        log.info("(getAllSubjectLecturers) Successfully retrieved all subject lecturers - total: {}", subjectLecturers.size());
        return subjectLecturers.stream().map(this::mapToSubjectLecturerResponseDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SubjectLecturerResponseDTO getById(SubjectLecturerId id) {
        validateId(id);
        log.info("(getSubjectLecturerById) Start - subjectId: {}, lecturerId: {}", id.getSubjectId(), id.getLecturerId());
        SubjectLecturer subjectLecturer = subjectLecturerRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("(getSubjectLecturerById) Subject Lecturer not found - subjectId: {}, lecturerId: {}",
                            id.getSubjectId(), id.getLecturerId());
                    return new BusinessException(InstructorCode.SUBJECT_LECTURER_NOT_FOUND);
                });
        log.info("(getSubjectLecturerById) Successfully retrieved subject lecturer - subjectId: {}, lecturerId: {}",
                id.getSubjectId(), id.getLecturerId());
        return mapToSubjectLecturerResponseDTO(subjectLecturer);
    }

    @Override
    @Transactional
    public SubjectLecturerResponseDTO update(SubjectLecturerId id, SubjectLecturerRequestDTO subjectLecturerRequestDTO) {
        validateId(id);
        validateSubjectLecturerRequestDTO(subjectLecturerRequestDTO);
        log.info("(updateSubjectLecturer) Start - subjectId: {}, lecturerId: {}", id.getSubjectId(), id.getLecturerId());
        SubjectLecturer subjectLecturer = subjectLecturerRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("(updateSubjectLecturer) Subject Lecturer not found - subjectId: {}, lecturerId: {}",
                            id.getSubjectId(), id.getLecturerId());
                    return new BusinessException(InstructorCode.SUBJECT_LECTURER_NOT_FOUND);
                });

        subjectLecturer.setSubjectId(subjectLecturerRequestDTO.getSubjectId());
        subjectLecturer.setLecturerId(subjectLecturerRequestDTO.getLecturerId());

        SubjectLecturer updatedSubjectLecturer = subjectLecturerRepository.save(subjectLecturer);
        log.info("(updateSubjectLecturer) Successfully updated subject lecturer - subjectId: {}, lecturerId: {}",
                updatedSubjectLecturer.getSubjectId(), updatedSubjectLecturer.getLecturerId());
        return mapToSubjectLecturerResponseDTO(updatedSubjectLecturer);
    }

    @Override
    @Transactional
    public void delete(SubjectLecturerId id) {
        validateId(id);
        log.info("(deleteSubjectLecturer) Start - subjectId: {}, lecturerId: {}", id.getSubjectId(), id.getLecturerId());
        SubjectLecturer subjectLecturer = subjectLecturerRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("(deleteSubjectLecturer) Subject Lecturer not found - subjectId: {}, lecturerId: {}",
                            id.getSubjectId(), id.getLecturerId());
                    return new BusinessException(InstructorCode.SUBJECT_LECTURER_NOT_FOUND);
                });
        subjectLecturerRepository.delete(subjectLecturer);
        log.info("(deleteSubjectLecturer) Successfully deleted subject lecturer - subjectId: {}, lecturerId: {}",
                id.getSubjectId(), id.getLecturerId());
    }

    private SubjectLecturerResponseDTO mapToSubjectLecturerResponseDTO(SubjectLecturer subjectLecturer) {
        SubjectLecturerResponseDTO subjectLecturerResponseDTO = new SubjectLecturerResponseDTO();
        subjectLecturerResponseDTO.setSubjectId(subjectLecturer.getSubjectId());
        subjectLecturerResponseDTO.setLecturerId(subjectLecturer.getLecturerId());
        return subjectLecturerResponseDTO;
    }

    private void validateSubjectLecturerRequestDTO(SubjectLecturerRequestDTO dto) {
        if (dto.getSubjectId() == null || dto.getLecturerId() == null) {
            throw new BusinessException(InstructorCode.INVALID_REQUEST);
        }
    }

    private void validateId(SubjectLecturerId id) {
        if (id == null || id.getSubjectId() == null || id.getLecturerId() == null) {
            throw new BusinessException(InstructorCode.INVALID_REQUEST);
        }
    }
}
