package com.hhplus.cleanarchi.entity.course;

import com.hhplus.cleanarchi.entity.course.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_detail_id")
    private Long courseDetailId;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "enroll_start_date", nullable = false)
    private LocalDate enrollStartDate;

    @Column(name = "enroll_end_date", nullable = false)
    private LocalDate enrollEndDate;

    @Column(name = "enroll_count", nullable = false)
    private int enrollCount;
}