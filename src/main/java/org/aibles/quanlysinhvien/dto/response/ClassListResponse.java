package org.aibles.quanlysinhvien.dto.response;

import lombok.Data;

import java.util.List;
@Data
public class ClassListResponse {

    private List<ClassResponseDTO> classes;

    public ClassListResponse(List<ClassResponseDTO> classes) {
        this.classes = classes;
    }


}

