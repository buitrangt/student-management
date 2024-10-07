package org.aibles.quanlysinhvien.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class AttendanceRequestDTO {

    private Date attendanceDate;
    private String status;
    private Long courseId;
    private Long studentId; 
}

