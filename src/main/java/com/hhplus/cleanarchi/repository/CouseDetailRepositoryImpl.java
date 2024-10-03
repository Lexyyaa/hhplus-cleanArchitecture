package com.hhplus.cleanarchi.repository;

import com.hhplus.cleanarchi.entity.CourseDetail;
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
}
