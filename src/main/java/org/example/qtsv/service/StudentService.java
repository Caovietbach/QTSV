package org.example.qtsv.service;

import jakarta.transaction.Transactional;
import org.example.qtsv.entity.Student;
import org.example.qtsv.entity.StudentData;
import org.example.qtsv.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentService {
    @Autowired
    private StudentRepository repo;

    public Page<Student> listAllWithPage(Pageable pageable) {
        return repo.findAll(pageable);
    }
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

    public void saveEdit(int id, Student updatedStudent){
        Student existingStudent = get(id);
        existingStudent.setFirstName(updatedStudent.getFirstName());
        existingStudent.setLastName(updatedStudent.getLastName());
        existingStudent.setAge(updatedStudent.getAge());
        existingStudent.setStudentCode(updatedStudent.getStudentCode());
        existingStudent.setDepartment(updatedStudent.getDepartment());
        existingStudent.setMajor(updatedStudent.getMajor());
        existingStudent.setYear(updatedStudent.getYear());
        existingStudent.setCountry(updatedStudent.getCountry());
        existingStudent.setGpa(updatedStudent.getGpa());
        save(existingStudent);
    }
    public String validateInput(Student student, boolean isNew) {
        String err = null;

        if(student.getLastName() == null || student.getLastName().isEmpty()) {
            err = "Student must have a last name";
        }
        if(student.getFirstName() == null || student.getFirstName().isEmpty()) {
            err = "Student must have a first name";
        }
        if(student.getAge() <= 0) {
            err = "Age must be greater than 0";
        }
        if(student.getStudentCode() == null || student.getStudentCode().isEmpty()) {
            err = "Student must have a student code";
        }
        if(isNew && repo.existsByStudentCode(student.getStudentCode())) {
            err = "This student code has been used";
        }
        if(student.getDepartment() == null || student.getDepartment().isEmpty()) {
            err = "Student must have a department";
        }
        if(student.getMajor() == null || student.getMajor().isEmpty()) {
            err = "Student must have a major";
        }
        if(student.getCountry() == null || student.getCountry().isEmpty()) {
            err = "Student must have a country";
        }
        if(student.getGpa() == null || student.getGpa() < 0) {
            err = "Student's GPA must be above or equal to 0";
        }

        return err;
    }

    public List<Student> sortByLastYear(List<Student> allStudents) {
        return allStudents.stream()
                .filter(s -> s.getYear() == 4)
                .collect(Collectors.toList());
    }

    public List<Student> sortByLastYearAndCountry(List<Student> allStudents) {
        return allStudents.stream()
                .filter(s -> s.getYear() == 4 && "Ha Noi".equals(s.getCountry()))
                .collect(Collectors.toList());
    }

    public List<Student> sortByGPAFromHighToLow(List<Student> allStudents) {
        return allStudents.stream()
                .sorted((s1, s2) -> Double.compare(s2.getGpa(), s1.getGpa()))
                .collect(Collectors.toList());
    }

    public List<Student> sortByGPAFromHighToLowAndLastName(List<Student> allStudents) {
        return allStudents.stream()
                .sorted((s1, s2) -> {
                    int gpaComparison = Double.compare(s2.getGpa(), s1.getGpa());
                    if (gpaComparison != 0) {
                        return gpaComparison;
                    } else {
                        return s1.getLastName().compareTo(s2.getLastName());
                    }
                })
                .collect(Collectors.toList());
    }

    public List<Student> sortByLastName() {
        return repo.findAllByOrderByLastNameAsc();
    }

    public List<Student> search( Student student, List<Student> allStudents){
        List<Student> searchedStudents = allStudents.stream()
                .filter(s -> (student.getFirstName() == null || s.getFirstName().contains(student.getFirstName())))
                .filter(s -> (student.getLastName() == null || s.getLastName().contains(student.getLastName())))
                .filter(s -> (Integer.valueOf(student.getAge()) == 0 || s.getAge() == Integer.valueOf(student.getAge())))
                .filter(s -> (student.getStudentCode() == null || s.getStudentCode().contains(student.getStudentCode())))
                .filter(s -> (Integer.valueOf(student.getYear()) == 0 || s.getYear() == Integer.valueOf(student.getYear())))
                .filter(s -> (student.getMajor() == null || s.getMajor().equals(student.getMajor())))
                .filter(s -> (student.getCountry() == null || s.getCountry().equals(student.getCountry())))
                .collect(Collectors.toList());
        return searchedStudents;
    }

    public Page<Student> getPage(List<Student> students, Pageable pageable) {
        int total = students.size();
        List<Student> paginatedList = students.stream()
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(paginatedList, pageable, total);
    }

    public StudentData getContent(Page<Student> students){
        StudentData data = new StudentData(students.getTotalElements(),students.getTotalPages(), students.getSize(), students.getContent());
        return data;
    }



}