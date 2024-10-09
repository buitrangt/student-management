package org.aibles.quanlysinhvien.dto.response;

import lombok.Data;

import java.util.List;


@Data
public class StudentCourseListResponse {

    private List<StudentCourseResponseDTO> studentCourses;

    public StudentCourseListResponse(List<StudentCourseResponseDTO> studentCourses) {
        this.studentCourses = studentCourses;
    }


}
