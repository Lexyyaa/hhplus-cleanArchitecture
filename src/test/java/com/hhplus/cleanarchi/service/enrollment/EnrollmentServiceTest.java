package com.hhplus.cleanarchi.service.enrollment;

import com.hhplus.cleanarchi.entity.course.Course;
import com.hhplus.cleanarchi.entity.course.CourseDetail;
import com.hhplus.cleanarchi.entity.enrollment.Enrollment;
import com.hhplus.cleanarchi.entity.user.User;
import com.hhplus.cleanarchi.repository.course.CourseDetailRepository;
import com.hhplus.cleanarchi.repository.enrollment.EnrollmentRepository;
import com.hhplus.cleanarchi.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EnrollmentServiceTest {

    @Mock
    private CourseDetailRepository courseDetailRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    // 성공 케이스: 강의를 신청하는 로직
    @Test
    @DisplayName("[성공] 강의 신청")
    public void 강의_신청() {
        // Given
        Long userId = 1L;
        Long courseId = 1L;
        LocalDate today = LocalDate.now();

        // Mocked User and CourseDetail entities
        User mockUser = new User();
        mockUser.setUserId(userId);

        Course mockCourse = new Course(1L, "강사명1", "강의명1");
        CourseDetail mockCourseDetail = new CourseDetail(1L, mockCourse, today.minusDays(1), today.plusDays(5), 20);

        // Repository에 대한 Mock 설정
        when(userRepository.findById(userId)).thenReturn(mockUser);
        when(courseDetailRepository.findCourseDetailWithLock(courseId, today)).thenReturn(mockCourseDetail);
        when(enrollmentRepository.existsByUserAndCourseDetail(mockUser, mockCourseDetail)).thenReturn(false);
        when(enrollmentRepository.saveWithLock(any(Enrollment.class))).thenReturn(new Enrollment());

        // When
        Enrollment result = enrollmentService.enrollCourse(userId, courseId);

        // Then
        assertNotNull(result);  // 신청이 성공했는지 확인
    }

    // 실패 케이스: 이미 신청한 강의를 다시 신청하려고 할 때
    @Test
    @DisplayName("[실패] 강의_신청_이미_신청한_강의를_신청함")
    public void 강의_신청_이미_신청한_강의를_신청함() {
        // Given
        Long userId = 1L;
        Long courseId = 1L;
        LocalDate today = LocalDate.now();

        // Mocked User and CourseDetail entities
        User mockUser = new User();
        mockUser.setUserId(userId);

        Course mockCourse = new Course(1L, "강사명1", "강의명1");
        CourseDetail mockCourseDetail = new CourseDetail(1L, mockCourse, today.minusDays(1), today.plusDays(5), 20);

        // 이미 신청한 상태로 가정
        when(userRepository.findById(userId)).thenReturn(mockUser);
        when(courseDetailRepository.findCourseDetailWithLock(courseId, today)).thenReturn(mockCourseDetail);
        when(enrollmentRepository.existsByUserAndCourseDetail(mockUser, mockCourseDetail)).thenReturn(true);

        // When / Then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            enrollmentService.enrollCourse(userId, courseId);
        });
        assertEquals("이미 신청한 강의입니다.", exception.getMessage());
    }

    // 실패 케이스: 신청 가능한 인원이 초과된 경우
    @Test
    @DisplayName("[실패] 강의_신청_신청_가능한_인원이_초과됨")
    public void testEnrollCourse_CapacityExceeded() {
        // Given
        Long userId = 1L;
        Long courseId = 1L;
        LocalDate today = LocalDate.now();

        // Mocked User and CourseDetail entities
        User mockUser = new User();
        mockUser.setUserId(userId);

        Course mockCourse = new Course(1L, "강사명1", "강의명1");
        CourseDetail mockCourseDetail = new CourseDetail(1L, mockCourse, today.minusDays(1), today.plusDays(5), 30); // 신청 인원 30명

        // Repository에 대한 Mock 설정
        when(userRepository.findById(userId)).thenReturn(mockUser);
        when(courseDetailRepository.findCourseDetailWithLock(courseId, today)).thenReturn(mockCourseDetail);
        when(enrollmentRepository.existsByUserAndCourseDetail(mockUser, mockCourseDetail)).thenReturn(false);

        // When / Then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            enrollmentService.enrollCourse(userId, courseId);
        });
        assertEquals("신청 가능한 인원이 초과되었습니다.", exception.getMessage());

    }
}