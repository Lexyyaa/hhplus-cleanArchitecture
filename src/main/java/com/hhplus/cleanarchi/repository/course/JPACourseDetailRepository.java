package com.hhplus.cleanarchi.repository.course;

import com.hhplus.cleanarchi.entity.course.CourseDetail;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface JPACourseDetailRepository extends JpaRepository<CourseDetail, Long> {
    @Query("SELECT cd FROM CourseDetail cd WHERE :date BETWEEN cd.enrollStartDate AND cd.enrollEndDate AND cd.enrollCount < 30")
    List<CourseDetail> findAvailableCoursesByDateAndCapacity(@Param("date") LocalDate date);

    @Lock(LockModeType.PESSIMISTIC_WRITE) //
    CourseDetail save(CourseDetail enrollment);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT cd FROM CourseDetail cd WHERE cd.courseDetailId = :courseDetailId AND :now BETWEEN cd.enrollStartDate AND cd.enrollEndDate")
    Optional<CourseDetail> findCourseDetailWithLock(@Param("courseDetailId") Long courseDetailId, @Param("now") LocalDate now);
}
