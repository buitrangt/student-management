package org.aibles.quanlysinhvien.dto.response;

import lombok.Data;

@Data
public class SubjectResponseDTO {
    private Integer subjectId;
    private String subjectName;
    private Integer credit;
    private Integer departmentId;
}
