package com.hhplus.cleanarchi.service;

import com.hhplus.cleanarchi.dto.course.CourseResponseDTO;
import com.hhplus.cleanarchi.entity.course.Course;
import com.hhplus.cleanarchi.entity.course.CourseDetail;
import com.hhplus.cleanarchi.repository.course.CourseDetailRepository;
import com.hhplus.cleanarchi.service.course.CourseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseDetailRepository courseDetailRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    @DisplayName("[성공] 신청가능한 강의목록 조회(해당날짜에 신청 가능한 강의가 존재)")
    public void 신청가능한_강의목록_조회_날짜있음() {
        // Given
        LocalDate testDate = LocalDate.of(2024, 10, 2);

        Course mockCourse = new Course(1L, "강사명1", "강의명1");
        CourseDetail mockCourseDetail = new CourseDetail(1L, mockCourse, testDate.minusDays(1), testDate.plusDays(5), 20);

        when(courseDetailRepository.findAvailableCoursesByDateAndCapacity(testDate))
                .thenReturn(Arrays.asList(mockCourseDetail));

        // When
        List<CourseResponseDTO> result = courseService.getAvailableCourses(testDate);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("강사명1", result.get(0).getInstructorName());
        assertEquals(1, result.get(0).getCourseDetails().size());
        assertEquals(20, result.get(0).getCourseDetails().get(0).getEnrollCount());
    }

    @Test
    @DisplayName("[실패] 신청가능한 강의목록 조회(해당날짜에 신청 가능한 강의가 없음)")
    public void 신청가능한_강의목록_조회_날짜없음() {
        // Given
        LocalDate testDate = LocalDate.of(2024, 10, 2);

        // Repository가 빈 리스트를 반환하는 경우
        when(courseDetailRepository.findAvailableCoursesByDateAndCapacity(testDate))
                .thenReturn(Collections.emptyList());

        // When
        List<CourseResponseDTO> result = courseService.getAvailableCourses(testDate);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}