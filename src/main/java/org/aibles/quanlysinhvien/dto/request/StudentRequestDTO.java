package org.aibles.quanlysinhvien.dto.request;


import lombok.Data;

import java.util.Date;

@Data
public class StudentRequestDTO {
    private String fullName;
    private Date dateOfBirth;
    private String gender;
    private String address;
    private String email;
    private String phoneNumber;
    private Integer classId;
}

