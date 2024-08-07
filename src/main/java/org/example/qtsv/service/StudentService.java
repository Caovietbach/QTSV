package org.example.qtsv.service;

import jakarta.transaction.Transactional;
import org.example.qtsv.ValidateException;
import org.example.qtsv.entity.Student;
import org.example.qtsv.entity.StudentData;
import org.example.qtsv.repository.ProcessDataInterface;
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
public class StudentService implements ProcessDataInterface {
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
    public void validateInput(Student student, boolean isNew) {
        if (student.getLastName() == null || student.getLastName().trim().isEmpty()) {
            throw new ValidateException("Student must have a last name");
        }
        if (student.getFirstName() == null || student.getFirstName().trim().isEmpty()) {
            throw new ValidateException("Student must have a first name");
        }
        if (student.getAge() <= 0 ) {
            throw new ValidateException("Age must be greater than 0");
        }
        if (student.getStudentCode() == null || student.getStudentCode().trim().isEmpty()) {
            throw new ValidateException("Student must have a student code");
        }
        if (isNew && repo.existsByStudentCode(student.getStudentCode())) {
            throw new ValidateException("This student code has been used");
        }
        if (student.getDepartment() == null || student.getDepartment().trim().isEmpty()) {
            throw new ValidateException("Student must have a department");
        }
        if (student.getMajor() == null || student.getMajor().trim().isEmpty()) {
            throw new ValidateException("Student must have a major");
        }
        if (student.getYear() <= 0) {
            throw new ValidateException("Year must be greater than 0");
        }
        if (student.getCountry() == null || student.getCountry().trim().isEmpty()) {
            throw new ValidateException("Student must have a country");
        }
        if (student.getGpa() < 0) {
            throw new ValidateException("Student's GPA must be above or equal to 0");
        }
    }

    @Override
    public List<Student> processDataByFilteringLastYear(List<Student> allStudents) {
        return allStudents.stream()
                .filter(s -> s.getYear() == 4)
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> processDataBySortingCountry(List<Student> allStudents) {
        return allStudents.stream()
                .sorted((s1, s2) -> {
                    return s1.getCountry().compareTo(s2.getCountry());
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> processDataBySortingGPAFromHighToLow(List<Student> allStudents) {
        return allStudents.stream()
                .sorted((s1, s2) -> Double.compare(s2.getGpa(), s1.getGpa()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> processDataBySortingGPAFromHighToLowAndLastName(List<Student> allStudents) {
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

    public List<Student> sortByLastName(List<Student> studentList) {
        return studentList.stream().sorted((s1,s2) -> {
            return s1.getLastName().compareTo(s2.getLastName());
        }).collect(Collectors.toList());
    }

    public List<Student> search( Student student){
        List<Student> searchedStudents = repo.searchBy(student.getFirstName(),student.getLastName(),student.getAge(),
                student.getStudentCode(),student.getYear(),student.getMajor(),
                student.getCountry());
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