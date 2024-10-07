package org.aibles.quanlysinhvien.entity;


import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "subject_lecturer")
@IdClass(SubjectLecturerId.class)
public class SubjectLecturer {

    @Id
    @Column(name = "subject_id")
    private Integer subjectId;

    @Id
    @Column(name = "lecturer_id")
    private Integer lecturerId;
}

