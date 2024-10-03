package com.hhplus.cleanarchi.controller.enrollment;

import com.hhplus.cleanarchi.dto.enroll.EnrollResponseDTO;
import com.hhplus.cleanarchi.entity.enrollment.Enrollment;
import com.hhplus.cleanarchi.service.enrollment.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    /**
     * 강의 신청
     */
    @PostMapping("/enroll/{userId}")
    public EnrollResponseDTO enrollCourse(@PathVariable Long userId, @RequestParam Long courseDetailId) {
        return enrollmentService.enrollCourse(userId, courseDetailId);
    }

    /**
     * 신청한 강의 목록 조회
     */
    @GetMapping("/list/{userId}")
    public List<EnrollResponseDTO> enrollList(@PathVariable Long userId) {
        return enrollmentService.getUserEnrollments(userId);
    }
}
