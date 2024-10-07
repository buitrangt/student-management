package org.aibles.quanlysinhvien.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.aibles.quanlysinhvien.dto.request.CourseRequestDTO;
import org.aibles.quanlysinhvien.dto.response.CourseResponseDTO;
import org.aibles.quanlysinhvien.entity.Course;
import org.aibles.quanlysinhvien.exception.BusinessException;
import org.aibles.quanlysinhvien.exception.InstructorCode;
import org.aibles.quanlysinhvien.repository.CourseRepository;
import org.aibles.quanlysinhvien.repository.LecturerRepository;
import org.aibles.quanlysinhvien.repository.SubjectRepository;
import org.aibles.quanlysinhvien.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final SubjectRepository subjectRepository;
    private final LecturerRepository lecturerRepository;

    public CourseServiceImpl(CourseRepository courseRepository, SubjectRepository subjectRepository, LecturerRepository lecturerRepository) {
        this.courseRepository = courseRepository;
        this.subjectRepository = subjectRepository;
        this.lecturerRepository = lecturerRepository;
    }

    @Transactional
    @Override
    public CourseResponseDTO create(CourseRequestDTO courseRequestDTO) {
        log.info("(createCourse) Start - courseRequestDTO: {}", courseRequestDTO);

        validateCourseRequest(courseRequestDTO);
        checkSubjectExists(courseRequestDTO.getSubjectId());
        checkLecturerExists(courseRequestDTO.getLecturerId());

        Course course = new Course();
        course.setCourseName(courseRequestDTO.getCourseName());
        course.setSubjectId(courseRequestDTO.getSubjectId());
        course.setLecturerId(courseRequestDTO.getLecturerId());
        course.setSemester(courseRequestDTO.getSemester());
        course.setAcademicYear(courseRequestDTO.getAcademicYear());

        Course savedCourse = courseRepository.save(course);
        log.info("(createCourse) Successfully created course - courseId: {}", savedCourse.getCourseId());

        return mapToCourseResponseDTO(savedCourse);
    }

    @Transactional
    @Override
    public CourseResponseDTO update(int courseId, CourseRequestDTO courseRequestDTO) {
        log.info("(updateCourse) Start - courseId: {}, courseRequestDTO: {}", courseId, courseRequestDTO);

        validateCourseRequest(courseRequestDTO);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BusinessException(InstructorCode.COURSE_NOT_FOUND));

        checkSubjectExists(courseRequestDTO.getSubjectId());
        checkLecturerExists(courseRequestDTO.getLecturerId());

        course.setCourseName(courseRequestDTO.getCourseName());
        course.setSubjectId(courseRequestDTO.getSubjectId());
        course.setLecturerId(courseRequestDTO.getLecturerId());
        course.setSemester(courseRequestDTO.getSemester());
        course.setAcademicYear(courseRequestDTO.getAcademicYear());

        Course updatedCourse = courseRepository.save(course);
        log.info("(updateCourse) Successfully updated course - courseId: {}", updatedCourse.getCourseId());

        return mapToCourseResponseDTO(updatedCourse);
    }

    @Transactional
    @Override
    public void delete(int courseId) {
        log.info("(deleteCourse) Start - courseId: {}", courseId);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BusinessException(InstructorCode.COURSE_NOT_FOUND));

        courseRepository.delete(course);
        log.info("(deleteCourse) Successfully deleted course - courseId: {}", courseId);
    }

    @Override
    public CourseResponseDTO getById(int courseId) {
        log.info("(getCourseById) Start - courseId: {}", courseId);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new BusinessException(InstructorCode.COURSE_NOT_FOUND));

        log.info("(getCourseById) Successfully retrieved course - courseId: {}", courseId);
        return mapToCourseResponseDTO(course);
    }

    @Override
    public List<CourseResponseDTO> getAll() {
        log.info("(getAllCourses) Start - retrieving all courses");

        List<Course> courses = courseRepository.findAll();
        List<CourseResponseDTO> courseResponseDTOs = courses.stream()
                .map(this::mapToCourseResponseDTO)
                .collect(Collectors.toList());

        log.info("(getAllCourses) Successfully retrieved all courses - total: {}", courseResponseDTOs.size());
        return courseResponseDTOs;
    }

    private void validateCourseRequest(CourseRequestDTO courseRequestDTO) {
        log.info("(validateCourseRequest) Validating request - courseRequestDTO: {}", courseRequestDTO);

        if (!StringUtils.hasText(courseRequestDTO.getCourseName())) {
            log.error("(validateCourseRequest) Course name is empty");
            throw new BusinessException(InstructorCode.INVALID_REQUEST);
        }
        if (courseRequestDTO.getSubjectId() == null) {
            log.error("(validateCourseRequest) Subject ID is null");
            throw new BusinessException(InstructorCode.INVALID_REQUEST);
        }
        if (courseRequestDTO.getLecturerId() == null) {
            log.error("(validateCourseRequest) Lecturer ID is null");
            throw new BusinessException(InstructorCode.INVALID_REQUEST);
        }
        if (!StringUtils.hasText(courseRequestDTO.getSemester())) {
            log.error("(validateCourseRequest) Semester is empty");
            throw new BusinessException(InstructorCode.INVALID_REQUEST);
        }
        if (!StringUtils.hasText(courseRequestDTO.getAcademicYear())) {
            log.error("(validateCourseRequest) Academic year is empty");
            throw new BusinessException(InstructorCode.INVALID_REQUEST);
        }
    }

    private void checkSubjectExists(int subjectId) {
        log.info("(checkSubjectExists) Checking if subject exists - subjectId: {}", subjectId);
        if (!subjectRepository.existsById(subjectId)) {
            log.error("(checkSubjectExists) Subject not found - subjectId: {}", subjectId);
            throw new BusinessException(InstructorCode.SUBJECT_NOT_FOUND);
        }
    }

    private void checkLecturerExists(int lecturerId) {
        log.info("(checkLecturerExists) Checking if lecturer exists - lecturerId: {}", lecturerId);
        if (!lecturerRepository.existsById(lecturerId)) {
            log.error("(checkLecturerExists) Lecturer not found - lecturerId: {}", lecturerId);
            throw new BusinessException(InstructorCode.LECTURER_NOT_FOUND);
        }
    }

    private CourseResponseDTO mapToCourseResponseDTO(Course course) {
        log.debug("(mapToCourseResponseDTO) Mapping course to DTO - courseId: {}", course.getCourseId());

        CourseResponseDTO courseResponseDTO = new CourseResponseDTO();
        courseResponseDTO.setCourseId(course.getCourseId());
        courseResponseDTO.setCourseName(course.getCourseName());
        courseResponseDTO.setSubjectId(course.getSubjectId());
        courseResponseDTO.setLecturerId(course.getLecturerId());
        courseResponseDTO.setSemester(course.getSemester());
        courseResponseDTO.setAcademicYear(course.getAcademicYear());
        return courseResponseDTO;
    }
}
