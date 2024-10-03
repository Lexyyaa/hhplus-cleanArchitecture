package com.hhplus.cleanarchi.dto.enroll;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollResponseDTO {
    private Long enrollmentId;
    private String instructorName;
    private String courseName;
}
