package org.aibles.quanlysinhvien.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.aibles.quanlysinhvien.dto.request.ClassRequestDTO;
import org.aibles.quanlysinhvien.dto.response.ClassResponseDTO;
import org.aibles.quanlysinhvien.dto.response.DepartmentResponseDTO;
import org.aibles.quanlysinhvien.entity.ClassEntity;
import org.aibles.quanlysinhvien.entity.Department;
import org.aibles.quanlysinhvien.exception.BusinessException;
import org.aibles.quanlysinhvien.exception.InstructorCode;
import org.aibles.quanlysinhvien.repository.ClassRepository;
import org.aibles.quanlysinhvien.repository.DepartmentRepository;
import org.aibles.quanlysinhvien.service.ClassService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;
    private final DepartmentRepository departmentRepository;

    public ClassServiceImpl(ClassRepository classRepository, DepartmentRepository departmentRepository) {
        this.classRepository = classRepository;
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    @Override
    public ClassResponseDTO create(ClassRequestDTO classRequestDTO) {
        log.info("(create) Start - classRequestDTO: {}", classRequestDTO);

        if (!StringUtils.hasText(classRequestDTO.getClassName()) || classRequestDTO.getDepartmentId() == null) {
            log.error("(create) Invalid request data - className: {}, departmentId: {}", classRequestDTO.getClassName(), classRequestDTO.getDepartmentId());
            throw new BusinessException(InstructorCode.INVALID_REQUEST);
        }

        Department department = departmentRepository.findById(classRequestDTO.getDepartmentId()).orElseThrow(() -> {
            log.error("(create) Department not found - departmentId: {}", classRequestDTO.getDepartmentId());
            return new BusinessException(InstructorCode.DEPARTMENT_NOT_FOUND);
        });

        ClassEntity classEntity = new ClassEntity();
        classEntity.setClassName(classRequestDTO.getClassName());
        classEntity.setDepartmentId(classRequestDTO.getDepartmentId());

        ClassEntity savedClass = classRepository.save(classEntity);
        log.info("(create) Successfully created class - classId: {}", savedClass.getClassId());

        return mapToClassResponseDTO(savedClass, department);
    }

    @Override
    public ClassResponseDTO getById(int classId) {
        log.info("(getById) Start - classId: {}", classId);

        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(() -> {
            log.error("(getById) Class not found - classId: {}", classId);
            throw new BusinessException(InstructorCode.CLASS_NOT_FOUND);
        });

        Department department = departmentRepository.findById(classEntity.getDepartmentId()).orElseThrow(() -> {
            log.error("(getById) Department not found - departmentId: {}", classEntity.getDepartmentId());
            throw new BusinessException(InstructorCode.DEPARTMENT_NOT_FOUND);
        });

        log.info("(getById) Successfully retrieved class - classId: {}", classId);
        return mapToClassResponseDTO(classEntity, department);
    }

    @Override
    public List<ClassResponseDTO> getAll() {
        log.info("(getAll) Start - retrieving all classes");

        List<ClassEntity> classEntities = classRepository.findAll();
        List<ClassResponseDTO> classResponseDTOs = classEntities.stream().map(classEntity -> {
            Department department = departmentRepository.findById(classEntity.getDepartmentId()).orElseThrow(() -> {
                log.error("(getAll) Department not found - departmentId: {}", classEntity.getDepartmentId());
                throw new BusinessException(InstructorCode.DEPARTMENT_NOT_FOUND);
            });
            return mapToClassResponseDTO(classEntity, department);
        }).collect(Collectors.toList());

        log.info("(getAll) Successfully retrieved all classes - total: {}", classResponseDTOs.size());
        return classResponseDTOs;
    }

    @Transactional
    @Override
    public ClassResponseDTO update(int classId, ClassRequestDTO classRequestDTO) {
        log.info("(update) Start - classId: {}, classRequestDTO: {}", classId, classRequestDTO);

        if (!StringUtils.hasText(classRequestDTO.getClassName()) || classRequestDTO.getDepartmentId() == null) {
            log.error("(update) Invalid request data - classId: {}, className: {}, departmentId: {}", classId, classRequestDTO.getClassName(), classRequestDTO.getDepartmentId());
            throw new BusinessException(InstructorCode.INVALID_REQUEST);
        }

        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(() -> {
            log.error("(update) Class not found - classId: {}", classId);
            throw new BusinessException(InstructorCode.CLASS_NOT_FOUND);
        });

        Department department = departmentRepository.findById(classRequestDTO.getDepartmentId()).orElseThrow(() -> {
            log.error("(update) Department not found - departmentId: {}", classRequestDTO.getDepartmentId());
            throw new BusinessException(InstructorCode.DEPARTMENT_NOT_FOUND);
        });

        classEntity.setClassName(classRequestDTO.getClassName());
        classEntity.setDepartmentId(classRequestDTO.getDepartmentId());

        ClassEntity updatedClass = classRepository.save(classEntity);
        log.info("(update) Successfully updated class - classId: {}", updatedClass.getClassId());

        return mapToClassResponseDTO(updatedClass, department);
    }

    @Transactional
    @Override
    public void delete(int classId) {
        log.info("(delete) Start - classId: {}", classId);

        ClassEntity classEntity = classRepository.findById(classId).orElseThrow(() -> {
            log.error("(delete) Class not found - classId: {}", classId);
            throw new BusinessException(InstructorCode.CLASS_NOT_FOUND);
        });

        classRepository.delete(classEntity);
        log.info("(delete) Successfully deleted class - classId: {}", classId);
    }

    @Override
    public List<ClassResponseDTO> searchByName(String className) {
        log.info("(searchByName) Start - className: {}", className);

        List<ClassEntity> classEntities = classRepository.findByClassNameContainingIgnoreCase(className);
        List<ClassResponseDTO> classResponseDTOs = classEntities.stream().map(classEntity -> {
            Department department = departmentRepository.findById(classEntity.getDepartmentId())
                    .orElseThrow(() -> {
                        log.error("(searchByName) Department not found - departmentId: {}", classEntity.getDepartmentId());
                        throw new BusinessException(InstructorCode.DEPARTMENT_NOT_FOUND);
                    });
            return mapToClassResponseDTO(classEntity, department);
        }).collect(Collectors.toList());

        log.info("(searchByName) Successfully searched classes - total: {}", classResponseDTOs.size());
        return classResponseDTOs;
    }

    private ClassResponseDTO mapToClassResponseDTO(ClassEntity savedClass, Department department) {
        ClassResponseDTO responseDTO = new ClassResponseDTO();
        responseDTO.setClassId(savedClass.getClassId());
        responseDTO.setClassName(savedClass.getClassName());
        responseDTO.setDepartmentId(String.valueOf(savedClass.getDepartmentId()));

        DepartmentResponseDTO departmentDTO = new DepartmentResponseDTO();
        departmentDTO.setDepartmentId(department.getDepartmentId());
        departmentDTO.setDepartmentName(department.getDepartmentName());

        log.debug("(mapToClassResponseDTO) Mapped class and department to DTO - classId: {}, departmentId: {}", savedClass.getClassId(), department.getDepartmentId());
        return responseDTO;
    }
}
