package org.aibles.quanlysinhvien.dto.response;

import lombok.Data;

import java.util.List;


@Data
public class SubjectLecturerListResponse {

    private List<SubjectLecturerResponseDTO> subjectLecturers;

    public SubjectLecturerListResponse(List<SubjectLecturerResponseDTO> subjectLecturers) {
        this.subjectLecturers = subjectLecturers;
    }

}

