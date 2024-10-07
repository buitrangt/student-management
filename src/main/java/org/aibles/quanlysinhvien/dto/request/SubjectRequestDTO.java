package org.aibles.quanlysinhvien.dto.request;

import lombok.Data;

@Data
public class SubjectRequestDTO {
    private String subjectName;
    private Integer credit;
    private Integer departmentId;
}
