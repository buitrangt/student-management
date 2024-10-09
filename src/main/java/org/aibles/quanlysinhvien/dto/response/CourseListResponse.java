package org.aibles.quanlysinhvien.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CourseListResponse {

    private List<CourseResponseDTO> courses;

    public CourseListResponse(List<CourseResponseDTO> courses) {
        this.courses = courses;
    }


}

