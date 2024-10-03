package com.hhplus.cleanarchi.repository.enrollment;

import com.hhplus.cleanarchi.entity.course.CourseDetail;
import com.hhplus.cleanarchi.entity.enrollment.Enrollment;
import com.hhplus.cleanarchi.entity.user.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

public interface JPAEnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByUserAndCourseDetail(User user, CourseDetail courseDetail);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Enrollment save(Enrollment enrollment);
    List<Enrollment> findAllByUser_UserId(Long userId);
}