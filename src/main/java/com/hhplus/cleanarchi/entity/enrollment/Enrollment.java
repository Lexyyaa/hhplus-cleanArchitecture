package com.hhplus.cleanarchi.entity.enrollment;

import com.hhplus.cleanarchi.entity.user.User;
import com.hhplus.cleanarchi.entity.course.CourseDetail;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Long enrollmentId;

    @ManyToOne
    @JoinColumn(name = "course_detail_id")
    private CourseDetail courseDetail;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "instructor_name", nullable = false)
    private String instructorName;

    @Column(name = "course_name", nullable = false)
    private String courseName;
}