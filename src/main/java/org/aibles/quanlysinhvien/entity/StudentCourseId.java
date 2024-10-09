package org.aibles.quanlysinhvien.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourseId implements Serializable {
    private Integer studentId;
    private Integer courseId;


}
