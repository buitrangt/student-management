package org.aibles.quanlysinhvien.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.aibles.quanlysinhvien.dto.request.StudentRequestDTO;
import org.aibles.quanlysinhvien.dto.response.StudentResponseDTO;
import org.aibles.quanlysinhvien.entity.Student;
import org.aibles.quanlysinhvien.exception.exception.StudentNotFoundException;
import org.aibles.quanlysinhvien.repository.StudentRepository;
import org.aibles.quanlysinhvien.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional
    public StudentResponseDTO create(StudentRequestDTO studentRequestDTO) {
        log.info("(createStudent) Start - studentRequestDTO: {}", studentRequestDTO);

        Student student = new Student();
        student.setFullName(studentRequestDTO.getFullName());
        student.setDateOfBirth(studentRequestDTO.getDateOfBirth());
        student.setGender(Student.Gender.valueOf(studentRequestDTO.getGender()));
        student.setAddress(studentRequestDTO.getAddress());
        student.setEmail(studentRequestDTO.getEmail());
        student.setPhoneNumber(studentRequestDTO.getPhoneNumber());
        student.setClassId(studentRequestDTO.getClassId());

        Student savedStudent = studentRepository.save(student);
        log.info("(createStudent) Successfully created student - studentId: {}", savedStudent.getStudentId());

        return mapToStudentResponseDTO(savedStudent);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getAll() {
        log.info("(getAllStudents) Start - retrieving all students");
        List<Student> students = studentRepository.findAll();
        log.info("(getAllStudents) Successfully retrieved all students - total: {}", students.size());
        return students.stream().map(this::mapToStudentResponseDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponseDTO getById(int studentId) {
        log.info("(getStudentById) Start - studentId: {}", studentId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    log.error("(getStudentById) Student not found - studentId: {}", studentId);
                    return new StudentNotFoundException("Student with ID " + studentId + " not found.");
                });
        log.info("(getStudentById) Successfully retrieved student - studentId: {}", studentId);
        return mapToStudentResponseDTO(student);
    }

    @Override
    @Transactional
    public StudentResponseDTO update(int studentId, StudentRequestDTO studentRequestDTO) {
        log.info("(updateStudent) Start - studentId: {}", studentId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    log.error("(updateStudent) Student not found - studentId: {}", studentId);
                    return new StudentNotFoundException("Student with ID " + studentId + " not found.");
                });

        student.setFullName(studentRequestDTO.getFullName());
        student.setDateOfBirth(studentRequestDTO.getDateOfBirth());
        student.setGender(Student.Gender.valueOf(studentRequestDTO.getGender()));
        student.setAddress(studentRequestDTO.getAddress());
        student.setEmail(studentRequestDTO.getEmail());
        student.setPhoneNumber(studentRequestDTO.getPhoneNumber());
        student.setClassId(studentRequestDTO.getClassId());

        Student updatedStudent = studentRepository.save(student);
        log.info("(updateStudent) Successfully updated student - studentId: {}", updatedStudent.getStudentId());
        return mapToStudentResponseDTO(updatedStudent);
    }

    @Override
    @Transactional
    public void delete(int studentId) {
        log.info("(deleteStudent) Start - studentId: {}", studentId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    log.error("(deleteStudent) Student not found - studentId: {}", studentId);
                    return new StudentNotFoundException("Student with ID " + studentId + " not found.");
                });
        studentRepository.delete(student);
        log.info("(deleteStudent) Successfully deleted student - studentId: {}", studentId);
    }

    private StudentResponseDTO mapToStudentResponseDTO(Student student) {
        StudentResponseDTO studentResponseDTO = new StudentResponseDTO();
        studentResponseDTO.setStudentId(student.getStudentId());
        studentResponseDTO.setFullName(student.getFullName());
        studentResponseDTO.setDateOfBirth(student.getDateOfBirth());
        studentResponseDTO.setGender(student.getGender().name());
        studentResponseDTO.setAddress(student.getAddress());
        studentResponseDTO.setEmail(student.getEmail());
        studentResponseDTO.setPhoneNumber(student.getPhoneNumber());
        studentResponseDTO.setClassId(student.getClassId());
        return studentResponseDTO;
    }
}
