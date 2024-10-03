package com.hhplus.cleanarchi.controller.enrollment;

import com.hhplus.cleanarchi.entity.enrollment.Enrollment;
import com.hhplus.cleanarchi.service.enrollment.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping("/enroll/{userId}")
    public Enrollment enrollCourse(@PathVariable Long userId, @RequestParam Long courseDetailId) {
        return enrollmentService.enrollCourse(userId, courseDetailId);
    }
}
