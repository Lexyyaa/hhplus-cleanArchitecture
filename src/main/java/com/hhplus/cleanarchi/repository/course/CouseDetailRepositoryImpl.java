package com.hhplus.cleanarchi.repository.course;

import com.hhplus.cleanarchi.entity.course.CourseDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CouseDetailRepositoryImpl implements CourseDetailRepository {

    private final JPACourseDetailRepository jPACourseDetailRepository;

    @Override
    public List<CourseDetail> findAvailableCoursesByDateAndCapacity(LocalDate date) {
        return jPACourseDetailRepository.findAvailableCoursesByDateAndCapacity(date);
    }

    @Override
    public CourseDetail findCourseDetailWithLock(Long courseDetailId, LocalDate now) {
        return jPACourseDetailRepository.findCourseDetailWithLock(courseDetailId,now)
                .orElseThrow(() -> new RuntimeException("해당 강의가 존재하지 않거나 신청 기간이 아닙니다."));
    }

    @Override
    public CourseDetail saveWithLock(CourseDetail courseDetail) {
        return jPACourseDetailRepository.save(courseDetail);
    }
}
