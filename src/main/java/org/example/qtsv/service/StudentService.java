package org.example.qtsv.service;

import org.example.qtsv.api.StudentData;
import org.example.qtsv.entity.LastYearStudentEntity;
import org.example.qtsv.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {
    List<Student> sortByYearOfStudy(List<Student> students);
    List<Student> sortByCountryFirstAlphabet(List<Student> students);
    List<Student> sortByGPAFromHighToLow(List<Student> students);
    List<Student> sortByGPAFromHighToLowAndLastNameFirstAlphabet(List<Student> students);
    List<Student> sortByLastName(List<Student> students);

    void save(int id, LastYearStudentEntity lastYearStudent);
    void save(Student student);

    List<Student> search( Student student);

    List<LastYearStudentEntity> search(String thesisTitle);

    Page<Student> getPage(List<Student> students, Pageable pageable);
    StudentData getContent(Page<Student> students);

    // How to write a proper class inside interface
    //Using default class and static class
    //
    // Why abstract is a why interface has a
    /*
    public static void test {
        System.out.println("test");
    }

     */
    public static class test {
        static class B {
            public static String T() {
                return "T";
            }
        }


    }

    default void defaultMethod() {
        System.out.println("This is a default method.");
    }

    default void print(){
        System.out.println("This is second default method");
    }

}
