package org.example.qtsv.service;

import jakarta.transaction.Transactional;
import org.example.qtsv.entity.Student;
import org.example.qtsv.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.*;

import static org.apache.coyote.http11.Constants.a;

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

    public List<Student> sortByLastYear() {
        List<Student> sortStudents = new ArrayList<Student>();
        List<Student> allStudents = listAll();
        for( Student s : allStudents ){
            if(s.getYear() == 4){
                sortStudents.add(s);
            }
        }
        return sortStudents;
    }

    public List<Student> sortByLastYearAndCountry() {
        List<Student> sortStudents = new ArrayList<Student>();
        List<Student> allStudents = listAll();
        for( Student s : allStudents){
            if(s.getAge() == 4 && s.getCountry() == "Ha Noi"){
                sortStudents.add(s);
            }
        }
        return sortStudents;
    }

    public List<Student> sortByGPAFromHighToLow() {
        List<Student> allStudents = listAll();
        List<Student> sortStudents = new ArrayList<Student>();
        for (int i = 0; i < allStudents.size(); i++) {
            for (int j = i + 1; j < allStudents.size(); j++) {
                if (allStudents.get(j).getGpa() > allStudents.get(i).getGpa()) {
                    Student temp = allStudents.get(j);
                    allStudents.set(j, allStudents.get(i));
                    allStudents.set(i, temp);
                }
            }
        }
        sortStudents = allStudents;
        return sortStudents;
    }

    public List<Student> sortByGPAFromHighToLowAndLastName() {
        List<Student> allStudents = listAll();
        List<Student> sortStudents = new ArrayList<Student>();
        for (int i = 0; i < allStudents.size(); i++) {
            for (int j = i + 1; j < allStudents.size(); j++) {
                if (allStudents.get(j).getGpa() > allStudents.get(i).getGpa()) {
                    Student temp = allStudents.get(j);
                    allStudents.set(j, allStudents.get(i));
                    allStudents.set(i, temp);
                }
                if (allStudents.get(j).getGpa() == allStudents.get(i).getGpa()){
                    int a = allStudents.get(j).getLastName().compareTo(allStudents.get(i).getLastName());
                    if(a>0){
                        Student temp = allStudents.get(j);
                        allStudents.set(j, allStudents.get(i));
                        allStudents.set(i, temp);
                    }
                }
            }
        }
        sortStudents = allStudents;
        return sortStudents;
    }

    public List<Student> search(String search) {
        List<Student> allStudents = listAll();
        List<Student> foundStudents = new ArrayList<Student>();
        for (Student s : allStudents) {
            if (s.getFirstName().contains(search) || s.getLastName().contains(search) || s.getStudentCode().contains(search) || s.getMajor().equals(search) || s.getCountry().equals(search)) {
                foundStudents.add(s);
            }
            if (isNumber(search)) {
                if(s.getAge() == Integer.valueOf(search) || s.getYear() == Integer.valueOf(search)){
                    foundStudents.add(s);
                }
            }
        }
        return  foundStudents;
    }

    public boolean isNumber(String s){
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
