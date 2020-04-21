package org.utc.k59.it3.dto;

public class CandidateDTO {
    private Integer id;
    private String name;
    private String provinceName;
    private String birthDate;
    private String gender;
    private Double mathMark;
    private Double physicsMark;
    private Double chemistryMark;

    public CandidateDTO(Integer id,
                        String name,
                        String provinceName,
                        String birthDate,
                        String gender,
                        Double mathMark,
                        Double physicsMark,
                        Double chemistryMark) {
        this.id = id;
        this.name = name;
        this.provinceName = provinceName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.mathMark = mathMark;
        this.physicsMark = physicsMark;
        this.chemistryMark = chemistryMark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Double getMathMark() {
        return mathMark;
    }

    public void setMathMark(Double mathMark) {
        this.mathMark = mathMark;
    }

    public Double getPhysicsMark() {
        return physicsMark;
    }

    public void setPhysicsMark(Double physicsMark) {
        this.physicsMark = physicsMark;
    }

    public Double getChemistryMark() {
        return chemistryMark;
    }

    public void setChemistryMark(Double chemistryMark) {
        this.chemistryMark = chemistryMark;
    }
}
