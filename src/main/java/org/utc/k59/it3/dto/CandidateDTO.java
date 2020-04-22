package org.utc.k59.it3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateDTO {
    private Integer id;
    private String name;
    private String provinceName;
    private String birthDate;
    private String gender;
    private Double mathMark;
    private Double physicsMark;
    private Double chemistryMark;
    private Double totalMark;
}
