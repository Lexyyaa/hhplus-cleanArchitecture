package com.hhplus.cleanarchi.service;
import com.hhplus.cleanarchi.dto.CourseDetailResponseDTO;
import com.hhplus.cleanarchi.dto.CourseResponseDTO;
import com.hhplus.cleanarchi.entity.Course;
import com.hhplus.cleanarchi.entity.CourseDetail;
import com.hhplus.cleanarchi.repository.CourseDetailRepository;
import com.hhplus.cleanarchi.repository.CouseDetailImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CouseDetailImpl couseDetailImpl;

    public List<CourseResponseDTO> getAvailableCourses(LocalDate date) {
        List<CourseDetail> availableCourseDetails = couseDetailImpl.findAvailableCoursesByDateAndCapacity(date);

        return availableCourseDetails.stream()
                .collect(Collectors.groupingBy(CourseDetail::getCourse))
                .entrySet().stream()
                .map(entry -> {
                    Course course = entry.getKey();
                    List<CourseDetailResponseDTO> courseDetailDTOs = entry.getValue().stream()
                            .map(cd -> new CourseDetailResponseDTO(
                                    cd.getCourseDetailId(),
                                    cd.getEnrollStartDate().toString(),
                                    cd.getEnrollEndDate().toString(),
                                    cd.getEnrollCount()
                            ))
                            .collect(Collectors.toList());
                    return new CourseResponseDTO(course.getCourseId(), course.getInstructorName(), course.getCourseName(), courseDetailDTOs);
                })
                .collect(Collectors.toList());
    }
}
