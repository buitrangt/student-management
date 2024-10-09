package org.aibles.quanlysinhvien.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.aibles.quanlysinhvien.dto.request.AttendanceRequestDTO;
import org.aibles.quanlysinhvien.dto.response.AttendanceResponseDTO;
import org.aibles.quanlysinhvien.entity.Attendance;
import org.aibles.quanlysinhvien.entity.AttendanceStatus;
import org.aibles.quanlysinhvien.entity.Course;
import org.aibles.quanlysinhvien.entity.Student;
import org.aibles.quanlysinhvien.exception.BusinessException;
import org.aibles.quanlysinhvien.exception.InstructorCode;
import org.aibles.quanlysinhvien.repository.AttendanceRepository;
import org.aibles.quanlysinhvien.repository.CourseRepository;
import org.aibles.quanlysinhvien.repository.StudentRepository;
import org.aibles.quanlysinhvien.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public AttendanceServiceImpl(AttendanceRepository attendanceRepository,
                                 CourseRepository courseRepository,
                                 StudentRepository studentRepository) {
        this.attendanceRepository = attendanceRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional
    public AttendanceResponseDTO create(AttendanceRequestDTO attendanceRequestDTO) {
        log.info("(create) Start - attendanceRequestDTO: {}", attendanceRequestDTO);

        validateAttendanceRequest(attendanceRequestDTO);

        Course course = checkCourseExists(attendanceRequestDTO.getCourseId());
        Student student = checkStudentExists(attendanceRequestDTO.getStudentId());

        Attendance attendance = new Attendance();
        attendance.setAttendanceDate(attendanceRequestDTO.getAttendanceDate());
        attendance.setStatus(AttendanceStatus.valueOf(attendanceRequestDTO.getStatus()));
        attendance.setCourse(course);
        attendance.setStudent(student);

        Attendance savedAttendance = attendanceRepository.save(attendance);
        log.info("(create) Successfully created attendance - attendanceId: {}", savedAttendance.getAttendanceId());

        return mapToResponseDTO(savedAttendance);
    }

    @Override
    @Transactional
    public AttendanceResponseDTO update(Long attendanceId, AttendanceRequestDTO attendanceRequestDTO) {
        log.info("(update) Start - attendanceId: {}", attendanceId);

        validateAttendanceRequest(attendanceRequestDTO);

        Attendance attendance = checkAttendanceExists(attendanceId);

        Course course = checkCourseExists(attendanceRequestDTO.getCourseId());
        Student student = checkStudentExists(attendanceRequestDTO.getStudentId());

        attendance.setAttendanceDate(attendanceRequestDTO.getAttendanceDate());
        attendance.setStatus(AttendanceStatus.valueOf(attendanceRequestDTO.getStatus()));
        attendance.setCourse(course);
        attendance.setStudent(student);

        Attendance updatedAttendance = attendanceRepository.save(attendance);
        log.info("(update) Successfully updated attendance - attendanceId: {}", updatedAttendance.getAttendanceId());

        return mapToResponseDTO(updatedAttendance);
    }

    @Override
    @Transactional
    public void delete(Long attendanceId) {
        log.info("(delete) Start - attendanceId: {}", attendanceId);

        Attendance attendance = checkAttendanceExists(attendanceId);

        attendanceRepository.delete(attendance);
        log.info("(delete) Successfully deleted attendance - attendanceId: {}", attendanceId);
    }

    @Override
    @Transactional(readOnly = true)
    public AttendanceResponseDTO getById(Long attendanceId) {
        log.info("(getById) Start - attendanceId: {}", attendanceId);

        Attendance attendance = checkAttendanceExists(attendanceId);

        log.info("(getById) Successfully retrieved attendance - attendanceId: {}", attendanceId);
        return mapToResponseDTO(attendance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceResponseDTO> getAll() {
        log.info("(getAll) Start - retrieving all attendance records");

        List<Attendance> attendances = attendanceRepository.findAll();
        return attendances.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    private Course checkCourseExists(Long courseId) {
        return courseRepository.findByCourseId(courseId)
                .orElseThrow(() -> new BusinessException(InstructorCode.COURSE_NOT_FOUND));
    }

    private Student checkStudentExists(Long studentId) {
        return studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new BusinessException(InstructorCode.STUDENT_NOT_FOUND));
    }

    private Attendance checkAttendanceExists(Long attendanceId) {
        return attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new BusinessException(InstructorCode.ATTENDANCE_NOT_FOUND));
    }

    private void validateAttendanceRequest(AttendanceRequestDTO attendanceRequestDTO) {
        if (attendanceRequestDTO == null) {
            throw new BusinessException(InstructorCode.INVALID_REQUEST);
        }
        if (attendanceRequestDTO.getAttendanceDate() == null) {
            throw new BusinessException(InstructorCode.INVALID_REQUEST);
        }
        if (attendanceRequestDTO.getStatus() == null || attendanceRequestDTO.getStatus().trim().isEmpty()) {
            throw new BusinessException(InstructorCode.INVALID_REQUEST);
        }
        if (attendanceRequestDTO.getCourseId() == null) {
            throw new BusinessException(InstructorCode.INVALID_REQUEST);
        }
        if (attendanceRequestDTO.getStudentId() == null) {
            throw new BusinessException(InstructorCode.INVALID_REQUEST);
        }
    }

    private AttendanceResponseDTO mapToResponseDTO(Attendance attendance) {
        AttendanceResponseDTO responseDTO = new AttendanceResponseDTO();
        responseDTO.setAttendanceId(attendance.getAttendanceId());
        responseDTO.setAttendanceDate(attendance.getAttendanceDate());
        responseDTO.setStatus(attendance.getStatus().name());
        responseDTO.setCourseName(attendance.getCourse().getCourseName());
        responseDTO.setStudentName(attendance.getStudent().getFullName());
        return responseDTO;
    }
}

