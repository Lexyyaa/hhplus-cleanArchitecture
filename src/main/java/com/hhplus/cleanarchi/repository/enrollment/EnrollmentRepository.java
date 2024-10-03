package com.hhplus.cleanarchi.repository.enrollment;

import com.hhplus.cleanarchi.entity.course.CourseDetail;
import com.hhplus.cleanarchi.entity.enrollment.Enrollment;
import com.hhplus.cleanarchi.entity.user.User;

import java.util.List;

public interface EnrollmentRepository {
    boolean existsByUserAndCourseDetail(User user, CourseDetail courseDetail);

    Enrollment saveWithLock(Enrollment enrollment);

    List<Enrollment> getUserEnrollments(Long userId);
}

