package com.hhplus.cleanarchi.repository.enrollment;

import com.hhplus.cleanarchi.entity.course.CourseDetail;
import com.hhplus.cleanarchi.entity.enrollment.Enrollment;
import com.hhplus.cleanarchi.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EnrollmentRepositoryImpl implements EnrollmentRepository{

    private final JPAEnrollmentRepository jpaEnrollmentRepository;
    @Override
    public boolean existsByUserAndCourseDetail(User user, CourseDetail courseDetail) {
        return jpaEnrollmentRepository.existsByUserAndCourseDetail(user,courseDetail);
    }
    @Override
    public Enrollment saveWithLock(Enrollment enrollment) {
        return jpaEnrollmentRepository.save(enrollment);
    }
}
