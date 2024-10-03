package com.hhplus.cleanarchi.controller.course;

import com.hhplus.cleanarchi.dto.course.CourseResponseDTO;
import com.hhplus.cleanarchi.service.course.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    /**
     * 신청가능한 강의 목록 조회
     */
    @GetMapping("/available")
    public List<CourseResponseDTO> getAvailableCourses(@RequestParam("date") String dateStr) {
        LocalDate date = LocalDate.parse(dateStr);
        return courseService.getAvailableCourses(date);
    }
}
