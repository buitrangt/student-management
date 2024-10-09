package org.aibles.quanlysinhvien.dto.response;

import lombok.Data;

import java.util.List;


@Data
public class SubjectListResponse {

    private List<SubjectResponseDTO> subjects;

    public SubjectListResponse(List<SubjectResponseDTO> subjects) {
        this.subjects = subjects;
    }


}

