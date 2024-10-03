package com.hhplus.cleanarchi.service.enrollment;

import com.hhplus.cleanarchi.dto.course.CourseDetailResponseDTO;
import com.hhplus.cleanarchi.dto.enroll.EnrollResponseDTO;
import com.hhplus.cleanarchi.entity.course.CourseDetail;
import com.hhplus.cleanarchi.entity.enrollment.Enrollment;
import com.hhplus.cleanarchi.entity.user.User;
import com.hhplus.cleanarchi.repository.course.CourseDetailRepository;
import com.hhplus.cleanarchi.repository.enrollment.EnrollmentRepository;
import com.hhplus.cleanarchi.repository.user.UserRepository;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final CourseDetailRepository courseDetailRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Transactional
    public EnrollResponseDTO enrollCourse(Long userId, Long courseDetailId) {
        // 1. 사용자 확인
        User user = userRepository.findById(userId);

        // 2. 강의 상세 정보 조회 및 락 설정 (동시성 제어)
        CourseDetail courseDetail = courseDetailRepository.findCourseDetailWithLock(courseDetailId, LocalDate.now());

        // 3. 동일한 유저가 이미 신청했는지 확인
        boolean alreadyEnrolled = enrollmentRepository.existsByUserAndCourseDetail(user, courseDetail);
        if (alreadyEnrolled) {
            throw new IllegalStateException("이미 신청한 강의입니다.");
        }

        // 4. 신청 가능 인원 확인 (30명 초과 시 실패)
        if (courseDetail.getEnrollCount() >= 30) {
            throw new IllegalStateException("신청 가능한 인원이 초과되었습니다.");
        }

        // 5. 강의 신청 로직 처리 (Enrollment 생성)
        Enrollment enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setCourseDetail(courseDetail);
        enrollment.setInstructorName(courseDetail.getCourse().getInstructorName());
        enrollment.setCourseName(courseDetail.getCourse().getCourseName());

        enrollmentRepository.saveWithLock(enrollment);

        // 6. 신청 인원 증가 (동시성 제어)
        courseDetail.setEnrollCount(courseDetail.getEnrollCount() + 1);
        courseDetailRepository.saveWithLock(courseDetail);

        return new EnrollResponseDTO(enrollment.getEnrollmentId(), enrollment.getInstructorName(), enrollment.getCourseName());
    }

    @Transactional(readOnly = true)
    public List<EnrollResponseDTO> getUserEnrollments(Long userId) {
        // 1. 사용자 ID로 신청된 Enrollment 목록 조회
        List<Enrollment> enrollments = enrollmentRepository.getUserEnrollments(userId);

        // 2. Enrollment 목록을 EnrollResponseDTO로 변환
        return enrollments.stream()
                .map(EnrollmentService::convertToDto)
                .collect(Collectors.toList());
    }

    private static EnrollResponseDTO convertToDto(Enrollment enrollment) {
        return new EnrollResponseDTO(
                enrollment.getEnrollmentId(),
                enrollment.getInstructorName(),
                enrollment.getCourseName()
        );
    }
}

