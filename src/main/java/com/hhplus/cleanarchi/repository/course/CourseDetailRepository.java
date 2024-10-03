package com.hhplus.cleanarchi.repository.course;

import com.hhplus.cleanarchi.entity.course.CourseDetail;

import java.time.LocalDate;
import java.util.List;

public interface CourseDetailRepository {
    List<CourseDetail> findAvailableCoursesByDateAndCapacity(LocalDate date);
    CourseDetail findCourseDetailWithLock(Long courseDetailId, LocalDate now);
    CourseDetail saveWithLock(CourseDetail courseDetail);
}