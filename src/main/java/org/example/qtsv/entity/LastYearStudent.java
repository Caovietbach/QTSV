package org.example.qtsv.entity;

import jakarta.persistence.Entity;

@Entity
public class LastYearStudent extends Student {
    private String thesisTitle;

    public String getThesisTitle() {
        return thesisTitle;
    }

    public void setThesisTitle(String thesisTitle) {
        this.thesisTitle = thesisTitle;
    }

    public LastYearStudent(int id, String firstName, String lastName, int age, String studentCode, String department, String major, int year, String country, Float gpa, String thesisTitle) {
        super(id, firstName, lastName, age, studentCode, department, major, year, country, gpa);
        this.thesisTitle = thesisTitle;
    }

    public LastYearStudent() {

    }
}