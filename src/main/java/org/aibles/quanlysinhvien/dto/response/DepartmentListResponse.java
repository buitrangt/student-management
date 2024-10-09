package org.aibles.quanlysinhvien.dto.response;

import lombok.Data;

import java.util.List;


@Data
public class DepartmentListResponse {

    private List<DepartmentResponseDTO> departments;

    public DepartmentListResponse(List<DepartmentResponseDTO> departments) {
        this.departments = departments;
    }


}

