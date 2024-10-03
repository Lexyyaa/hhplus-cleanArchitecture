package com.hhplus.cleanarchi.dto.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDTO {
    private Long courseId;
    private String instructorName;
    private String courseName;
    private List<CourseDetailResponseDTO> courseDetails;
}