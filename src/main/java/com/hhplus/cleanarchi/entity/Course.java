package com.hhplus.cleanarchi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "instructor_name", nullable = false)
    private String instructorName;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<CourseDetail> courseDetails = new ArrayList<>();
}