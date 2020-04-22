package org.utc.k59.it3.dto;

import lombok.Data;

@Data
public class CandidateJsonDTO {
    private Integer id;
    private String name;
    private Integer provinceId;
    private String birthDate;
    private String gender;
    private Double mathMark;
    private Double physicsMark;
    private Double chemistryMark;
}
