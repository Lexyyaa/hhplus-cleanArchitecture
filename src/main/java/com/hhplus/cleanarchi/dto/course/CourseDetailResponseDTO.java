package com.hhplus.cleanarchi.dto.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetailResponseDTO {
    private Long courseDetailId;
    private String enrollStartDate;
    private String enrollEndDate;
    private int enrollCount;
}
