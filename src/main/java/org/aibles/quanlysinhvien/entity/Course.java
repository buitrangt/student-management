package org.aibles.quanlysinhvien.entity;


import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "subject_id")
    private Integer subjectId;

    @Column(name = "lecturer_id")
    private Integer lecturerId;

    @Column(name = "semester")
    private String semester;

    @Column(name = "academic_year")
    private String academicYear;
}

