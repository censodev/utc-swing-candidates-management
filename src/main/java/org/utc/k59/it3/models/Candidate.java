/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.utc.k59.it3.models;

import java.time.LocalDate;
import javax.persistence.*;

import org.utc.k59.it3.utils.Gender;

/**
 *
 * @author JewCat
 */
@Entity
@Table(name = "CANDIDATES")
public class Candidate {
    @Id
    @TableGenerator(name = "HIBERNATE_SEQUENCES", initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "HIBERNATE_SEQUENCES")
    @Column(name = "ID")
    private Integer id;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "PROVINCE_ID")
    private Integer provinceId;
    
    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;
    
    @Column(name = "GENDER")
    private String gender;
    
    @Column(name = "MATH_MARK")
    private Double mathMark;
    
    @Column(name = "PHYSICS_MARK")
    private Double physicsMark;
    
    @Column(name = "CHEMISTRY_MARK")
    private Double chemistryMark;

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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
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
        if (!isValidMark(mathMark))
            throw new NumberFormatException();
        else
            this.mathMark = mathMark;
    }

    public Double getPhysicsMark() {
        return physicsMark;
    }

    public void setPhysicsMark(Double physicsMark) {
        if (!isValidMark(physicsMark))
            throw new NumberFormatException();
        else
            this.physicsMark = physicsMark;
    }

    public Double getChemistryMark() {
        return chemistryMark;
    }

    public void setChemistryMark(Double chemistryMark) {
        if (!isValidMark(chemistryMark))
            throw new NumberFormatException();
        else
            this.chemistryMark = chemistryMark;
    }
    
    private boolean isValidMark(Double mark) {
        return mark <= 10 && mark >= 0;
    }
}
