package org.aibles.quanlysinhvien.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class StudentListResponse {
    private List<StudentResponseDTO> students;
    private int totalStudents;

    public StudentListResponse(List<StudentResponseDTO> students, int totalStudents) {
        this.students = students;
        this.totalStudents = totalStudents;
    }

}

