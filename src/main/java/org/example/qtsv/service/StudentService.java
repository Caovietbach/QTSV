package org.example.qtsv.service;

import jakarta.transaction.Transactional;
import org.example.qtsv.entity.Student;
import org.example.qtsv.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.*;

@Service
@Transactional
public class StudentService {
    @Autowired
    private StudentRepository repo;

    public List<Student> listAll() {
        return (List<Student>) repo.findAll();
    }

    public void save(Student student) {
        repo.save(student);
    }

    public Student get(long id) {
        return repo.findById(id).get();
    }

    public void delete(long id) {
        repo.deleteById(id);
    }




    public String validateNewInformation(Student student){
        String err = null;
        if(student.getLastName() == ""){
            err = "Student must have a last name";
            System.out.println("1");
        }
        if(student.getFirstName() == ""){
            err = "Student must have a first name";
            System.out.println("1");
        }
        if( String.valueOf(student.getAge()) == ""){
            err = "Student must have a first name";
            System.out.println("1");
        }
        if( student.getAge() <= 0){
            err = "Age must greater than 0";
        }
        if(student.getStudentCode() == ""){
            err = "Student must have a student code";
            System.out.println("2");
        }
        String sCode = student.getStudentCode();
        boolean sCodeDuplicate = repo.existsByStudentCode(sCode);
        if (sCodeDuplicate == true){
            err = "This student code has been used";
            System.out.println("3");
        }
        if(student.getDepartment() == ""){
            err = "Student must have a department";
            System.out.println("1");
        }
        if(student.getMajor() == ""){
            err = "Student must have a major";
        }
        if(student.getCountry() == ""){
            err = "Student must have a country";
        }
        if( student.getGpa() == null ){
            err = "Student must have a GPA";
        }
        if( student.getGpa() < 0){
            err = "Student's GPA must above or equal 0";
        }
        return err;
    }

    public String validateEditInformation(Student student){
        String err = null;
        if(student.getLastName() == ""){
            err = "Student must have a last name";
            System.out.println("1");
        }
        if(student.getFirstName() == ""){
            err = "Student must have a first name";
            System.out.println("1");
        }
        if( String.valueOf(student.getAge()) == ""){
            err = "Student must have a first name";
            System.out.println("1");
        }
        if( student.getAge() <= 0){
            err = "Age must greater than 0";
        }
        if(student.getStudentCode() == ""){
            err = "Student must have a student code";
            System.out.println("2");
        }
        if(student.getDepartment() == ""){
            err = "Student must have a department";
            System.out.println("1");
        }
        if(student.getMajor() == ""){
            err = "Student must have a major";
        }
        if(student.getCountry() == ""){
            err = "Student must have a country";
        }
        if( student.getGpa() == null ){
            err = "Student must have a GPA";
        }
        if( student.getGpa() < 0){
            err = "Student's GPA must above or equal 0";
        }
        return err;
    }
}
