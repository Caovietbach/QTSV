package org.example.qtsv.service;

import jakarta.transaction.Transactional;
import org.example.qtsv.entity.Student;
import org.example.qtsv.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

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
        return err;
    }

    public String validateEditInformation(Student student){
        String err = null;
        if(student.getFirstName().equals("")){
            err = "Student must have a first name";
            System.out.println("1");
        }
        if(student.getLastName().equals("")){
            err = "Student must have a last name";
            System.out.println("2");
        }
        return err;
    }
<<<<<<< Updated upstream
=======

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

    public List<Student> search( String firstName, String lastName, Integer age, String studentCode,
                                 Integer year, String major, String country){
        List<Student> studentsList = listAll();
        List<Student> searchedStudents = studentsList.stream()
                .filter(s -> (firstName == null || s.getFirstName().contains(firstName)))
                .filter(s -> (lastName == null || s.getLastName().contains(lastName)))
                .filter(s -> (age == null || s.getAge() == age))
                .filter(s -> (studentCode == null || s.getStudentCode().contains(studentCode)))
                .filter(s -> (year == null || s.getYear() == year))
                .filter(s -> (major == null || s.getMajor().equals(major)))
                .filter(s -> (country == null || s.getCountry().equals(country)))
                .collect(Collectors.toList());

        return searchedStudents;
    }

    public boolean isNumber(String s){
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

>>>>>>> Stashed changes
}
