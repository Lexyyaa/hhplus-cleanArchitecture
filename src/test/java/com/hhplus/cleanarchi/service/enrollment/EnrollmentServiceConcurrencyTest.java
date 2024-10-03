package com.hhplus.cleanarchi.service.enrollment;

import com.hhplus.cleanarchi.controller.enrollment.EnrollmentController;
import com.hhplus.cleanarchi.dto.enroll.EnrollResponseDTO;
import com.hhplus.cleanarchi.entity.course.CourseDetail;
import com.hhplus.cleanarchi.entity.enrollment.Enrollment;
import com.hhplus.cleanarchi.entity.user.User;
import com.hhplus.cleanarchi.repository.course.CourseDetailRepository;
import com.hhplus.cleanarchi.repository.enrollment.EnrollmentRepository;
import com.hhplus.cleanarchi.repository.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EnrollmentServiceConcurrencyTest {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private CourseDetailRepository courseDetailRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    @DisplayName("[동시성] 동시에_40명의_사용자가_신청할떄_30명만_성공")
    public void 동시에_40명의_사용자가_신청할떄_30명만_성공() throws InterruptedException {
        // Given
        Long courseDetailId = 1L;
        Long userId = 1L;

        ExecutorService executorService = Executors.newFixedThreadPool(40);
        CountDownLatch latch = new CountDownLatch(40);

        AtomicInteger successCount = new AtomicInteger(0);

        for (int i = 0; i < 40; i++) {
            final Long threadUserId = userId + i;
            executorService.submit(() -> {
                try {

                    enrollmentService.enrollCourse(threadUserId, courseDetailId);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        CourseDetail courseDetail = courseDetailRepository.findById(courseDetailId);
        // Then
        assertEquals(30, successCount.get());
        assertEquals(30, courseDetail.getEnrollCount());
    }

    @Test
    @Transactional
    @DisplayName("[동시성] 동일한_사용자가_동시에_강의를_신청")
    public void 동일한_사용자가_동시에_강의를_신청() throws InterruptedException {
        // Given
        Long userId = 1L;
        Long courseDetailId = 1L;

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            executorService.submit(() -> {
                try {
                    enrollmentService.enrollCourse(userId, courseDetailId);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        List<Enrollment> result = enrollmentRepository.getUserEnrollments(userId);

        // Then
        assertEquals(1, result.size());
    }
}
