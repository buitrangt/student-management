package org.aibles.quanlysinhvien.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.aibles.quanlysinhvien.dto.request.LecturerRequestDTO;
import org.aibles.quanlysinhvien.dto.response.LecturerResponseDTO;
import org.aibles.quanlysinhvien.entity.Lecturer;
import org.aibles.quanlysinhvien.exception.BusinessException;
import org.aibles.quanlysinhvien.exception.InstructorCode;
import org.aibles.quanlysinhvien.repository.LecturerRepository;
import org.aibles.quanlysinhvien.service.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LecturerServiceImpl implements LecturerService {

    private final LecturerRepository lecturerRepository;

    @Autowired
    public LecturerServiceImpl(LecturerRepository lecturerRepository) {
        this.lecturerRepository = lecturerRepository;
    }

    @Transactional
    @Override
    public LecturerResponseDTO create(LecturerRequestDTO lecturerRequestDTO) {
        log.info("(createLecturer) Start - lecturerRequestDTO: {}", lecturerRequestDTO);

        validateLecturerRequest(lecturerRequestDTO);

        Lecturer lecturer = new Lecturer();
        lecturer.setFullName(lecturerRequestDTO.getFullName());
        lecturer.setDateOfBirth(lecturerRequestDTO.getDateOfBirth());
        lecturer.setGender(Lecturer.Gender.valueOf(lecturerRequestDTO.getGender()));
        lecturer.setEmail(lecturerRequestDTO.getEmail());
        lecturer.setPhoneNumber(lecturerRequestDTO.getPhoneNumber());
        lecturer.setDepartmentId(lecturerRequestDTO.getDepartmentId());

        Lecturer savedLecturer = lecturerRepository.save(lecturer);

        log.info("(createLecturer) Successfully created lecturer - lecturerId: {}", savedLecturer.getLecturerId());
        return mapToLecturerResponseDTO(savedLecturer);
    }

    @Override
    public List<LecturerResponseDTO> getAll() {
        log.info("(getAllLecturers) Start - retrieving all lecturers");

        List<Lecturer> lecturers = lecturerRepository.findAll();
        List<LecturerResponseDTO> lecturerResponseDTOs = lecturers.stream()
                .map(this::mapToLecturerResponseDTO)
                .collect(Collectors.toList());

        log.info("(getAllLecturers) Successfully retrieved all lecturers - total: {}", lecturerResponseDTOs.size());
        return lecturerResponseDTOs;
    }

    @Override
    public LecturerResponseDTO getById(int lecturerId) {
        log.info("(getLecturerById) Start - lecturerId: {}", lecturerId);

        Lecturer lecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new BusinessException(InstructorCode.LECTURER_NOT_FOUND));
        log.info("(getLecturerById) Successfully retrieved lecturer - lecturerId: {}", lecturerId);
        return mapToLecturerResponseDTO(lecturer);
    }

    @Transactional
    @Override
    public LecturerResponseDTO update(int lecturerId, LecturerRequestDTO lecturerRequestDTO) {
        log.info("(updateLecturer) Start - lecturerId: {}, lecturerRequestDTO: {}", lecturerId, lecturerRequestDTO);

        validateLecturerRequest(lecturerRequestDTO);

        Lecturer lecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new BusinessException(InstructorCode.LECTURER_NOT_FOUND));

        lecturer.setFullName(lecturerRequestDTO.getFullName());
        lecturer.setDateOfBirth(lecturerRequestDTO.getDateOfBirth());
        lecturer.setGender(Lecturer.Gender.valueOf(lecturerRequestDTO.getGender()));
        lecturer.setEmail(lecturerRequestDTO.getEmail());
        lecturer.setPhoneNumber(lecturerRequestDTO.getPhoneNumber());
        lecturer.setDepartmentId(lecturerRequestDTO.getDepartmentId());

        Lecturer updatedLecturer = lecturerRepository.save(lecturer);

        log.info("(updateLecturer) Successfully updated lecturer - lecturerId: {}", lecturerId);
        return mapToLecturerResponseDTO(updatedLecturer);
    }

    @Transactional
    @Override
    public void delete(int lecturerId) {
        log.info("(deleteLecturer) Start - lecturerId: {}", lecturerId);

        Lecturer lecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new BusinessException(InstructorCode.LECTURER_NOT_FOUND));

        lecturerRepository.delete(lecturer);
        log.info("(deleteLecturer) Successfully deleted lecturer - lecturerId: {}", lecturerId);
    }

    private void validateLecturerRequest(LecturerRequestDTO lecturerRequestDTO) {
        log.info("(validateLecturerRequest) Validating lecturer request - lecturerRequestDTO: {}", lecturerRequestDTO);

        if (!StringUtils.hasText(lecturerRequestDTO.getFullName())) {
            log.error("(validateLecturerRequest) Full name must not be empty");
            throw new BusinessException(InstructorCode.INVALID_REQUEST);
        }
        if (!StringUtils.hasText(lecturerRequestDTO.getGender())) {
            log.error("(validateLecturerRequest) Gender must not be empty");
            throw new BusinessException(InstructorCode.INVALID_REQUEST);
        }
        if (!StringUtils.hasText(lecturerRequestDTO.getEmail())) {
            log.error("(validateLecturerRequest) Email must not be empty");
            throw new BusinessException(InstructorCode.INVALID_REQUEST);
        }
        if (!StringUtils.hasText(lecturerRequestDTO.getPhoneNumber())) {
            log.error("(validateLecturerRequest) Phone number must not be empty");
            throw new BusinessException(InstructorCode.INVALID_REQUEST);
        }
        if (lecturerRequestDTO.getDepartmentId() == null) {
            log.error("(validateLecturerRequest) Department ID must not be null");
            throw new BusinessException(InstructorCode.INVALID_REQUEST);
        }
    }

    private LecturerResponseDTO mapToLecturerResponseDTO(Lecturer lecturer) {
        log.debug("(mapToLecturerResponseDTO) Mapping lecturer to DTO - lecturerId: {}", lecturer.getLecturerId());

        LecturerResponseDTO lecturerResponseDTO = new LecturerResponseDTO();
        lecturerResponseDTO.setLecturerId(lecturer.getLecturerId());
        lecturerResponseDTO.setFullName(lecturer.getFullName());
        lecturerResponseDTO.setDateOfBirth(lecturer.getDateOfBirth());
        lecturerResponseDTO.setGender(lecturer.getGender().name());
        lecturerResponseDTO.setEmail(lecturer.getEmail());
        lecturerResponseDTO.setPhoneNumber(lecturer.getPhoneNumber());
        lecturerResponseDTO.setDepartmentId(lecturer.getDepartmentId());
        return lecturerResponseDTO;
    }
}
