package com.hhplus.cleanarchi.service.course;
import com.hhplus.cleanarchi.dto.course.CourseDetailResponseDTO;
import com.hhplus.cleanarchi.dto.course.CourseResponseDTO;
import com.hhplus.cleanarchi.entity.course.Course;
import com.hhplus.cleanarchi.entity.course.CourseDetail;
import com.hhplus.cleanarchi.repository.course.CourseDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseDetailRepository courseDetailRepository;

    public List<CourseResponseDTO> getAvailableCourses(LocalDate date) {
        List<CourseDetail> availableCourseDetails = courseDetailRepository.findAvailableCoursesByDateAndCapacity(date);

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
