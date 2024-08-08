package org.example.qtsv.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
public class LastYearStudent extends Student {

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

    public LastYearStudent() {

    }

    public LastYearStudent(int id, String firstName, String lastName, int age, String studentCode, String department, String major, int year, String country, Float gpa, String thesisCode, String thesisTitle) {
        super(id, firstName, lastName, age, studentCode, department, major, 4, country, gpa);
        this.thesisCode = thesisCode;
        this.thesisTitle = thesisTitle;
    }

}
