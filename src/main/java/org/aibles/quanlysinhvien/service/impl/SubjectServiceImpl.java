package org.aibles.quanlysinhvien.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.aibles.quanlysinhvien.dto.request.SubjectRequestDTO;
import org.aibles.quanlysinhvien.dto.response.SubjectResponseDTO;
import org.aibles.quanlysinhvien.entity.Subject;
import org.aibles.quanlysinhvien.exception.NotFoundException;
import org.aibles.quanlysinhvien.exception.exception.SubjectNotFoundException;
import org.aibles.quanlysinhvien.repository.SubjectRepository;
import org.aibles.quanlysinhvien.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    @Transactional
    public SubjectResponseDTO create(SubjectRequestDTO subjectRequestDTO) {
        log.info("(createSubject) Start - subjectRequestDTO: {}", subjectRequestDTO);

        Subject subject = new Subject();
        subject.setSubjectName(subjectRequestDTO.getSubjectName());
        subject.setCredit(subjectRequestDTO.getCredit());
        subject.setDepartmentId(subjectRequestDTO.getDepartmentId());

        Subject savedSubject = subjectRepository.save(subject);
        log.info("(createSubject) Successfully created subject - subjectId: {}", savedSubject.getSubjectId());

        return mapToSubjectResponseDTO(savedSubject);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectResponseDTO> getAll() {
        log.info("(getAllSubjects) Start - retrieving all subjects");
        List<Subject> subjects = subjectRepository.findAll();
        log.info("(getAllSubjects) Successfully retrieved all subjects - total: {}", subjects.size());
        return subjects.stream().map(this::mapToSubjectResponseDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SubjectResponseDTO getById(int subjectId) {
        log.info("(getSubjectById) Start - subjectId: {}", subjectId);
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> {
                    log.error("(getSubjectById) Subject not found - subjectId: {}", subjectId);
                    return new NotFoundException("Subject with ID " + subjectId + " not found.");
                });
        log.info("(getSubjectById) Successfully retrieved subject - subjectId: {}", subjectId);
        return mapToSubjectResponseDTO(subject);
    }

    @Override
    @Transactional
    public SubjectResponseDTO update(int subjectId, SubjectRequestDTO subjectRequestDTO) {
        log.info("(updateSubject) Start - subjectId: {}", subjectId);
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> {
                    log.error("(updateSubject) Subject not found - subjectId: {}", subjectId);
                    return new NotFoundException("Subject with ID " + subjectId + " not found.");
                });

        subject.setSubjectName(subjectRequestDTO.getSubjectName());
        subject.setCredit(subjectRequestDTO.getCredit());
        subject.setDepartmentId(subjectRequestDTO.getDepartmentId());

        Subject updatedSubject = subjectRepository.save(subject);
        log.info("(updateSubject) Successfully updated subject - subjectId: {}", updatedSubject.getSubjectId());
        return mapToSubjectResponseDTO(updatedSubject);
    }

    @Override
    @Transactional
    public void delete(int subjectId) {
        log.info("(deleteSubject) Start - subjectId: {}", subjectId);
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> {
                    log.error("(deleteSubject) Subject not found - subjectId: {}", subjectId);
                    return new NotFoundException("Subject with ID " + subjectId + " not found.");
                });
        subjectRepository.delete(subject);
        log.info("(deleteSubject) Successfully deleted subject - subjectId: {}", subjectId);
    }

    private SubjectResponseDTO mapToSubjectResponseDTO(Subject subject) {
        SubjectResponseDTO subjectResponseDTO = new SubjectResponseDTO();
        subjectResponseDTO.setSubjectId(subject.getSubjectId());
        subjectResponseDTO.setSubjectName(subject.getSubjectName());
        subjectResponseDTO.setCredit(subject.getCredit());
        subjectResponseDTO.setDepartmentId(subject.getDepartmentId());
        return subjectResponseDTO;
    }
}
