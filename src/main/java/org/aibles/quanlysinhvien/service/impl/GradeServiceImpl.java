package org.aibles.quanlysinhvien.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.aibles.quanlysinhvien.dto.request.GradeRequestDTO;
import org.aibles.quanlysinhvien.dto.response.GradeResponseDTO;
import org.aibles.quanlysinhvien.entity.Grade;
import org.aibles.quanlysinhvien.entity.GradeId;
import org.aibles.quanlysinhvien.exception.exception.GradeNotFoundException;
import org.aibles.quanlysinhvien.exception.exception.InvalidRequestException;
import org.aibles.quanlysinhvien.exception.exception.StudentNotFoundException;
import org.aibles.quanlysinhvien.exception.exception.CourseNotFoundException;
import org.aibles.quanlysinhvien.repository.CourseRepository;
import org.aibles.quanlysinhvien.repository.GradeRepository;
import org.aibles.quanlysinhvien.repository.StudentRepository;
import org.aibles.quanlysinhvien.service.GradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public GradeServiceImpl(GradeRepository gradeRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    @Override
    public GradeResponseDTO create(GradeRequestDTO gradeRequestDTO) {
        log.info("(createGrade) Start - gradeRequestDTO: {}", gradeRequestDTO);

        validateGradeRequest(gradeRequestDTO);

        checkStudentExists(gradeRequestDTO.getStudentId());
        checkCourseExists(gradeRequestDTO.getCourseId());

        Grade grade = new Grade();
        grade.setStudentId(gradeRequestDTO.getStudentId());
        grade.setCourseId(gradeRequestDTO.getCourseId());
        grade.setScore(gradeRequestDTO.getScore());

        Grade savedGrade = gradeRepository.save(grade);

        log.info("(createGrade) Successfully created grade - studentId: {}, courseId: {}", savedGrade.getStudentId(), savedGrade.getCourseId());

        return mapToGradeResponseDTO(savedGrade);
    }

    @Override
    public List<GradeResponseDTO> getAll() {
        log.info("(getAllGrades) Start - retrieving all grades");

        List<Grade> grades = gradeRepository.findAll();
        List<GradeResponseDTO> gradeResponseDTOs = grades.stream()
                .map(this::mapToGradeResponseDTO)
                .collect(Collectors.toList());

        log.info("(getAllGrades) Successfully retrieved all grades - total: {}", gradeResponseDTOs.size());
        return gradeResponseDTOs;
    }

    @Override
    public GradeResponseDTO getById(Integer studentId, Integer courseId) {
        log.info("(getGradeById) Start - studentId: {}, courseId: {}", studentId, courseId);

        GradeId gradeId = new GradeId(studentId, courseId);
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new GradeNotFoundException("Grade with student ID " + studentId + " and course ID " + courseId + " not found."));

        log.info("(getGradeById) Successfully retrieved grade - studentId: {}, courseId: {}", studentId, courseId);
        return mapToGradeResponseDTO(grade);
    }

    @Transactional
    @Override
    public GradeResponseDTO update(Integer studentId, Integer courseId, GradeRequestDTO gradeRequestDTO) {
        log.info("(updateGrade) Start - studentId: {}, courseId: {}, gradeRequestDTO: {}", studentId, courseId, gradeRequestDTO);

        validateGradeRequest(gradeRequestDTO);

        GradeId gradeId = new GradeId(studentId, courseId);
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new GradeNotFoundException("Grade with student ID " + studentId + " and course ID " + courseId + " not found."));

        grade.setScore(gradeRequestDTO.getScore());

        Grade updatedGrade = gradeRepository.save(grade);

        log.info("(updateGrade) Successfully updated grade - studentId: {}, courseId: {}", studentId, courseId);
        return mapToGradeResponseDTO(updatedGrade);
    }

    @Transactional
    @Override
    public void delete(Integer studentId, Integer courseId) {
        log.info("(deleteGrade) Start - studentId: {}, courseId: {}", studentId, courseId);

        GradeId gradeId = new GradeId(studentId, courseId);
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new GradeNotFoundException("Grade with student ID " + studentId + " and course ID " + courseId + " not found."));

        gradeRepository.delete(grade);
        log.info("(deleteGrade) Successfully deleted grade - studentId: {}, courseId: {}", studentId, courseId);
    }

    private void validateGradeRequest(GradeRequestDTO gradeRequestDTO) {
        log.info("(validateGradeRequest) Validating grade request - gradeRequestDTO: {}", gradeRequestDTO);

        if (gradeRequestDTO.getStudentId() == null || gradeRequestDTO.getCourseId() == null) {
            log.error("(validateGradeRequest) Student ID and Course ID must not be null");
            throw new InvalidRequestException("Student ID and Course ID must not be null.");
        }
        if (gradeRequestDTO.getScore() == null) {
            log.error("(validateGradeRequest) Score must not be null");
            throw new InvalidRequestException("Score must not be null.");
        }
    }

    private void checkStudentExists(Integer studentId) {
        log.info("(checkStudentExists) Checking if student exists - studentId: {}", studentId);

        if (!studentRepository.existsById(studentId)) {
            log.error("(checkStudentExists) Student not found - studentId: {}", studentId);
            throw new StudentNotFoundException("Student with ID " + studentId + " not found.");
        }
    }

    private void checkCourseExists(Integer courseId) {
        log.info("(checkCourseExists) Checking if course exists - courseId: {}", courseId);

        if (!courseRepository.existsById(courseId)) {
            log.error("(checkCourseExists) Course not found - courseId: {}", courseId);
            throw new CourseNotFoundException("Course with ID " + courseId + " not found.");
        }
    }

    private GradeResponseDTO mapToGradeResponseDTO(Grade grade) {
        log.debug("(mapToGradeResponseDTO) Mapping grade to DTO - studentId: {}, courseId: {}", grade.getStudentId(), grade.getCourseId());

        GradeResponseDTO gradeResponseDTO = new GradeResponseDTO();
        gradeResponseDTO.setStudentId(grade.getStudentId());
        gradeResponseDTO.setCourseId(grade.getCourseId());
        gradeResponseDTO.setScore(grade.getScore());
        return gradeResponseDTO;
    }
}
