package org.aibles.quanlysinhvien.dto.response;

import lombok.Data;

import java.util.List;


@Data
public class LecturerListResponse {

    private List<LecturerResponseDTO> lecturers;

    public LecturerListResponse(List<LecturerResponseDTO> lecturers) {
        this.lecturers = lecturers;
    }


}

