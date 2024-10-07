package org.aibles.quanlysinhvien.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.aibles.quanlysinhvien.dto.request.DepartmentRequestDTO;
import org.aibles.quanlysinhvien.dto.response.DepartmentResponseDTO;
import org.aibles.quanlysinhvien.entity.Department;
import org.aibles.quanlysinhvien.exception.exception.DepartmentNotFoundException;
import org.aibles.quanlysinhvien.exception.exception.InvalidRequestException;
import org.aibles.quanlysinhvien.repository.DepartmentRepository;
import org.aibles.quanlysinhvien.service.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    @Override
    public DepartmentResponseDTO create(DepartmentRequestDTO departmentRequestDTO) {
        log.info("(createDepartment) Start - departmentRequestDTO: {}", departmentRequestDTO);

        validateDepartmentRequest(departmentRequestDTO);

        Department department = new Department();
        department.setDepartmentName(departmentRequestDTO.getDepartmentName());

        Department savedDepartment = departmentRepository.save(department);
        log.info("(createDepartment) Successfully created department - departmentId: {}", savedDepartment.getDepartmentId());

        return mapToDepartmentResponseDTO(savedDepartment);
    }

    @Override
    public List<DepartmentResponseDTO> getAll() {
        log.info("(getAllDepartments) Start - retrieving all departments");

        List<Department> departments = departmentRepository.findAll();
        if (departments.isEmpty()) {
            log.warn("(getAllDepartments) No departments found.");
            throw new DepartmentNotFoundException("No departments found.");
        }

        List<DepartmentResponseDTO> departmentResponseDTOs = departments.stream()
                .map(this::mapToDepartmentResponseDTO)
                .collect(Collectors.toList());

        log.info("(getAllDepartments) Successfully retrieved all departments - total: {}", departmentResponseDTOs.size());
        return departmentResponseDTOs;
    }

    @Override
    public DepartmentResponseDTO getById(int departmentId) {
        log.info("(getDepartmentById) Start - departmentId: {}", departmentId);

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException("Department with ID " + departmentId + " not found."));

        log.info("(getDepartmentById) Successfully retrieved department - departmentId: {}", departmentId);
        return mapToDepartmentResponseDTO(department);
    }

    @Transactional
    @Override
    public DepartmentResponseDTO update(int departmentId, DepartmentRequestDTO departmentRequestDTO) {
        log.info("(updateDepartment) Start - departmentId: {}, departmentRequestDTO: {}", departmentId, departmentRequestDTO);

        validateDepartmentRequest(departmentRequestDTO);

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException("Department with ID " + departmentId + " not found."));

        department.setDepartmentName(departmentRequestDTO.getDepartmentName());

        Department updatedDepartment = departmentRepository.save(department);
        log.info("(updateDepartment) Successfully updated department - departmentId: {}", updatedDepartment.getDepartmentId());

        return mapToDepartmentResponseDTO(updatedDepartment);
    }

    @Transactional
    @Override
    public void delete(int departmentId) {
        log.info("(deleteDepartment) Start - departmentId: {}", departmentId);

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException("Department with ID " + departmentId + " not found."));

        departmentRepository.delete(department);
        log.info("(deleteDepartment) Successfully deleted department - departmentId: {}", departmentId);
    }

    private void validateDepartmentRequest(DepartmentRequestDTO departmentRequestDTO) {
        log.info("(validateDepartmentRequest) Validating request - departmentRequestDTO: {}", departmentRequestDTO);

        if (!StringUtils.hasText(departmentRequestDTO.getDepartmentName())) {
            log.error("(validateDepartmentRequest) Department name is empty");
            throw new InvalidRequestException("Department name must not be empty.");
        }
    }

    private DepartmentResponseDTO mapToDepartmentResponseDTO(Department department) {
        log.debug("(mapToDepartmentResponseDTO) Mapping department to DTO - departmentId: {}", department.getDepartmentId());

        DepartmentResponseDTO departmentResponseDTO = new DepartmentResponseDTO();
        departmentResponseDTO.setDepartmentId(department.getDepartmentId());
        departmentResponseDTO.setDepartmentName(department.getDepartmentName());
        return departmentResponseDTO;
    }
}
