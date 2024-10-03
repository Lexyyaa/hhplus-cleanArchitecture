package com.hhplus.cleanarchi.repository;

import com.hhplus.cleanarchi.entity.CourseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface JPACourseDetailRepository extends JpaRepository<CourseDetail, Long> {
    @Query("SELECT cd FROM CourseDetail cd WHERE :date BETWEEN cd.enrollStartDate AND cd.enrollEndDate AND cd.enrollCount < 30")
    List<CourseDetail> findAvailableCoursesByDateAndCapacity(@Param("date") LocalDate date);
}
