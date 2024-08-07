package org.example.qtsv.entity;

public class StudentPatron extends Student{
    private String patronName;

    public String getPatronName() {
        return patronName;
    }

    public void setPatronName(String patronName) {
        this.patronName = patronName;
    }

    public StudentPatron(int id, String firstName, String lastName, int age, String studentCode, String department, String major, int year, String country, Float gpa, String patronName) {
        super(id, firstName, lastName, age, studentCode, department, major, year, country, gpa);
        this.patronName = patronName;
    }

    public StudentPatron(){

    }

}
