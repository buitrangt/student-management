package org.aibles.quanlysinhvien.dto.response;


import lombok.Data;

import java.util.Date;

@Data
public class AttendanceResponseDTO {

    private Long attendanceId;
    private Date attendanceDate;
    private String status;
    private String courseName;
    private String studentName;
}

