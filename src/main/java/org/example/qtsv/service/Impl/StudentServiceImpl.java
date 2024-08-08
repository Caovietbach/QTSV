package org.example.qtsv.service.Impl;

import jakarta.transaction.Transactional;
import org.example.qtsv.ValidateException;
import org.example.qtsv.api.LastYearStudentData;
import org.example.qtsv.entity.LastYearStudent;
import org.example.qtsv.entity.Student;
import org.example.qtsv.api.StudentData;
import org.example.qtsv.repository.StudentRepository;
import org.example.qtsv.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository repo;
    public void save(Student student) {
        repo.save(student);
    }

    public void save(int id, String thesisCode, String thesisTitle){
        Student student = get(id);
        if (student.getAge() != 4){
            throw new ValidateException("This student must be in their last year!");
        } else if (thesisCode == null || thesisTitle == null){
            throw new ValidateException("Please enter thesis code and its tittle");
        } else {
            LastYearStudent lastYearStudent = new LastYearStudent();
            lastYearStudent.setId(student.getId());
            lastYearStudent.setFirstName(student.getFirstName());
            lastYearStudent.setLastName(student.getLastName());
            lastYearStudent.setAge(student.getAge());
            lastYearStudent.setStudentCode(student.getStudentCode());
            lastYearStudent.setDepartment(student.getDepartment());
            lastYearStudent.setMajor(student.getMajor());
            lastYearStudent.setYear(student.getYear());
            lastYearStudent.setCountry(student.getCountry());
            lastYearStudent.setGpa(student.getGpa());
            lastYearStudent.setThesisCode(thesisCode);
            lastYearStudent.setThesisTitle(thesisTitle);
            repo.save(lastYearStudent);
        }
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
    public List<Student> sortByYearOfStudy(List<Student> allStudents) {
        return allStudents.stream()
                .sorted((s1, s2) -> Integer.compare(s2.getYear(), s1.getYear()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> sortByCountryFirstAlphabet(List<Student> allStudents) {
        return allStudents.stream()
                .sorted((s1, s2) -> {
                    return s1.getCountry().compareTo(s2.getCountry());
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> sortByGPAFromHighToLow(List<Student> allStudents) {
        return allStudents.stream()
                .sorted((s1, s2) -> Double.compare(s2.getGpa(), s1.getGpa()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> sortByGPAFromHighToLowAndLastNameFirstAlphabet(List<Student> allStudents) {
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

    @Override
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

    public List<LastYearStudent> search(String thesisTitle){
        List<LastYearStudent> searchedStudents = repo.searchThesisBy(thesisTitle);
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

    public Page<LastYearStudent> getThesisPage(List<LastYearStudent> students, Pageable pageable) {
        int total = students.size();
        List<LastYearStudent> paginatedList = students.stream()
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(paginatedList, pageable, total);
    }

    public StudentData getContent(Page<Student> students){
        StudentData data = new StudentData(students.getTotalElements(),students.getTotalPages(), students.getSize(), students.getContent());
        return data;
    }

    public LastYearStudentData getThesis(Page<LastYearStudent> students){
        LastYearStudentData data = new LastYearStudentData(students.getTotalElements(),students.getTotalPages(), students.getSize(), students.getContent());
        return data;
    }








}