package org.utc.k59.it3.dto;

public class CandidateJsonDTO {
    private Integer id;
    private String name;
    private Integer provinceId;
    private String birthDate;
    private String gender;
    private Double mathMark;
    private Double physicsMark;

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

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
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

    private Double chemistryMark;
}
