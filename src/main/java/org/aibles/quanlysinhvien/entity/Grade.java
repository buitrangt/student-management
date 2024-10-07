package org.aibles.quanlysinhvien.entity;


import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "grade")
@IdClass(GradeId.class)
public class Grade {

    @Id
    @Column(name = "student_id")
    private Integer studentId;

    @Id
    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "score")
    private Float score;
}

