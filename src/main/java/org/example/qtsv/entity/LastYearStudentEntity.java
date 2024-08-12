package org.example.qtsv.entity;

import jakarta.persistence.*;

@Entity
public class LastYearStudentEntity extends Student {

    private String thesisCode;
    private String thesisTitle;

    public String getThesisCode() {
        return thesisCode;
    }

    public void setThesisCode(String thesisCode) {
        this.thesisCode = thesisCode;
    }

    public String getThesisTitle() {
        return thesisTitle;
    }

    public void setThesisTitle(String thesisTitle) {
        this.thesisTitle = thesisTitle;
    }

    public LastYearStudentEntity() {

    }

    public LastYearStudentEntity(int id, String firstName, String lastName, int age, String studentCode, String department, String major, int year, String country, Float gpa, String thesisCode, String thesisTitle) {
        super(id, firstName, lastName, age, studentCode, department, major, year, country, gpa);
        this.thesisCode = thesisCode;
        this.thesisTitle = thesisTitle;
    }

}
