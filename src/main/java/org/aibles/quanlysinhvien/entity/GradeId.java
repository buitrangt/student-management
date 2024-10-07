package org.aibles.quanlysinhvien.entity;


import lombok.Data;
import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeId implements Serializable {

    private Integer studentId;
    private Integer courseId;
}


