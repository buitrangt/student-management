package org.aibles.quanlysinhvien.dto.response;


import lombok.Data;

@Data
public class CourseResponseDTO {

    private Integer courseId;
    private Integer subjectId;
    private Integer lecturerId;
    private String semester;
    private String academicYear;
}

