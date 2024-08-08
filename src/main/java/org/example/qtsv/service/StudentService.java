package org.example.qtsv.service;

import org.example.qtsv.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> sortByYearOfStudy(List<Student> students);
    List<Student> sortByCountryFirstAlphabet(List<Student> students);
    List<Student> sortByGPAFromHighToLow(List<Student> students);
    List<Student> sortByGPAFromHighToLowAndLastNameFirstAlphabet(List<Student> students);
    List<Student> sortByLastName(List<Student> students);


}
