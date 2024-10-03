package com.hhplus.cleanarchi.repository;

import com.hhplus.cleanarchi.entity.CourseDetail;

import java.time.LocalDate;
import java.util.List;

public interface CourseDetailRepository {
    List<CourseDetail> findAvailableCoursesByDateAndCapacity(LocalDate date);
}