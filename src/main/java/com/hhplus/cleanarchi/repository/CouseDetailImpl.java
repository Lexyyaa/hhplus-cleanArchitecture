package com.hhplus.cleanarchi.repository;

import com.hhplus.cleanarchi.entity.CourseDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CouseDetailImpl implements CourseDetailRepository {

    private JPACourseDetailRepository jPACourseDetailRepository;

    @Override
    public List<CourseDetail> findAvailableCoursesByDateAndCapacity(LocalDate date) {
        return jPACourseDetailRepository.findAvailableCoursesByDateAndCapacity(date);
    }
}
