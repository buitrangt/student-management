package org.aibles.quanlysinhvien.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.aibles.quanlysinhvien.dto.request.StudentCourseRequestDTO;
import org.aibles.quanlysinhvien.dto.response.StudentCourseResponseDTO;
import org.aibles.quanlysinhvien.entity.StudentCourse;
import org.aibles.quanlysinhvien.entity.StudentCourseId;
import org.aibles.quanlysinhvien.exception.BusinessException;
import org.aibles.quanlysinhvien.exception.InstructorCode;
import org.aibles.quanlysinhvien.repository.CourseRepository;
import org.aibles.quanlysinhvien.repository.StudentCourseRepository;
import org.aibles.quanlysinhvien.repository.StudentRepository;
import org.aibles.quanlysinhvien.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StudentCourseServiceImpl implements StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public StudentCourseServiceImpl(StudentCourseRepository studentCourseRepository,
                                    StudentRepository studentRepository,
                                    CourseRepository courseRepository) {
        this.studentCourseRepository = studentCourseRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    @Override
    public StudentCourseResponseDTO create(StudentCourseRequestDTO studentCourseRequestDTO) {
        log.info("(createStudentCourse) Start - studentCourseRequestDTO: {}", studentCourseRequestDTO);

        checkStudentExists(studentCourseRequestDTO.getStudentId());
        checkCourseExists(studentCourseRequestDTO.getCourseId());

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(studentCourseRequestDTO.getStudentId());
        studentCourse.setCourseId(studentCourseRequestDTO.getCourseId());

        StudentCourse savedStudentCourse = studentCourseRepository.save(studentCourse);
        log.info("(createStudentCourse) Successfully created StudentCourse - studentId: {}, courseId: {}",
                savedStudentCourse.getStudentId(), savedStudentCourse.getCourseId());

        return mapToStudentCourseResponseDTO(savedStudentCourse);
    }

    @Override
    public List<StudentCourseResponseDTO> getAll() {
        log.info("(getAllStudentCourses) Start - retrieving all student courses");
        List<StudentCourse> studentCourses = studentCourseRepository.findAll();
        log.info("(getAllStudentCourses) Successfully retrieved all student courses - total: {}", studentCourses.size());
        return studentCourses.stream().map(this::mapToStudentCourseResponseDTO).collect(Collectors.toList());
    }

    @Override
    public StudentCourseResponseDTO getById(int studentId, int courseId) {
        log.info("(getStudentCourseById) Start - studentId: {}, courseId: {}", studentId, courseId);
        StudentCourseId studentCourseId = new StudentCourseId(studentId, courseId);
        StudentCourse studentCourse = studentCourseRepository.findById(studentCourseId)
                .orElseThrow(() -> new BusinessException(InstructorCode.STUDENT_COURSE_NOT_FOUND));
        log.info("(getStudentCourseById) Successfully retrieved StudentCourse - studentId: {}, courseId: {}", studentId, courseId);
        return mapToStudentCourseResponseDTO(studentCourse);
    }

    @Transactional
    @Override
    public void delete(int studentId, int courseId) {
        log.info("(deleteStudentCourse) Start - studentId: {}, courseId: {}", studentId, courseId);
        StudentCourseId studentCourseId = new StudentCourseId(studentId, courseId);
        StudentCourse studentCourse = studentCourseRepository.findById(studentCourseId)
                .orElseThrow(() -> new BusinessException(InstructorCode.STUDENT_COURSE_NOT_FOUND));
        studentCourseRepository.delete(studentCourse);
        log.info("(deleteStudentCourse) Successfully deleted StudentCourse - studentId: {}, courseId: {}", studentId, courseId);
    }

    private void checkStudentExists(Integer studentId) {
        if (!studentRepository.existsById(studentId)) {
            log.error("(checkStudentExists) Student not found - studentId: {}", studentId);
            throw new BusinessException(InstructorCode.STUDENT_NOT_FOUND);
        }
        log.info("(checkStudentExists) Student found - studentId: {}", studentId);
    }

    private void checkCourseExists(Integer courseId) {
        if (!courseRepository.existsById(courseId)) {
            log.error("(checkCourseExists) Course not found - courseId: {}", courseId);
            throw new BusinessException(InstructorCode.COURSE_NOT_FOUND);
        }
        log.info("(checkCourseExists) Course found - courseId: {}", courseId);
    }

    private StudentCourseResponseDTO mapToStudentCourseResponseDTO(StudentCourse studentCourse) {
        StudentCourseResponseDTO studentCourseResponseDTO = new StudentCourseResponseDTO();
        studentCourseResponseDTO.setStudentId(studentCourse.getStudentId());
        studentCourseResponseDTO.setCourseId(studentCourse.getCourseId());
        return studentCourseResponseDTO;
    }
}
