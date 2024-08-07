package org.example.qtsv.controller;

import org.example.qtsv.ApiResponse;
import org.example.qtsv.ValidateException;
import org.example.qtsv.entity.Student;
import org.example.qtsv.entity.StudentData;
import org.example.qtsv.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/students")
public class StudentControllerNew {
    private final StudentService service;
    private static final int FILTER_BY_LAST_YEAR = 1;
    private static final int SORT_BY_LAST_YEAR_AND_COUNTRY = 2;
    private static final int SORT_BY_GPA_HIGH_TO_LOW = 3;
    private static final int SORT_BY_GPA_HIGH_TO_LOW_AND_LAST_NAME = 4;

    @Autowired
    public StudentControllerNew(StudentService service) {
        this.service = service;
    }


    @GetMapping("/")
    public ApiResponse<StudentData> showAllStudents(@RequestParam(value = "processData", required = false) Integer processData, @RequestParam(value = "page", defaultValue = "0") int page,
                                                        @RequestParam(value = "size", defaultValue = "10") int size, @ModelAttribute Student s) {
        Pageable pageable = PageRequest.of(page, size);
        List<Student> studentList = new ArrayList<Student>();

        //Magic Number
        // Request Life Cycle

        if (s.getFirstName() != null || s.getLastName() != null ||  Integer.valueOf(s.getAge()) != 0 ||
                s.getStudentCode() != null || Integer.valueOf(s.getYear()) != 0 || s.getMajor() !=null || s.getCountry() != null) {
            studentList = service.search(s);
        }

        if (processData != null){
            if(processData == FILTER_BY_LAST_YEAR){
                studentList = service.processDataByFilteringLastYear(studentList);
            }
            if(processData == SORT_BY_LAST_YEAR_AND_COUNTRY){
                studentList = service.processDataBySortingCountry(studentList);
            }
            if(processData == SORT_BY_GPA_HIGH_TO_LOW){
                studentList = service.processDataBySortingGPAFromHighToLow(studentList);
            }
            if(processData == SORT_BY_GPA_HIGH_TO_LOW_AND_LAST_NAME){
                studentList = service.processDataBySortingGPAFromHighToLowAndLastName(studentList);
            }
        }
        studentList = service.sortByLastName(studentList);
        Page<Student> students = service.getPage(studentList,pageable);
        System.out.println(s);
        return new ApiResponse<>(true, "Thực hiện thành công", service.getContent(students));
    }

    @PostMapping("/add")
    public String addStudent(@RequestBody Student student) {
        try {
            service.validateInput(student, true);
            service.save(student);
        } catch (Exception e){
            return "An error occurred while validating input: " + e.getMessage();
        }
        return "Student saved successfully";
    }

    @PutMapping("/edit/{id}")
    public String editStudent(@PathVariable(name = "id") int id, @RequestBody Student updatedStudent) {
        try {
            service.validateInput(updatedStudent, false);
            service.save(updatedStudent);
        } catch (Exception e){
            return "An error occurred while validating input: " + e.getMessage();
        }
        return "Student saved successfully";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteStudent(@PathVariable(name = "id") int id) {
        Student existingStudent = service.get(id);
        if (existingStudent == null){
            return "Student not found";
        } else {
            service.delete(id);
            return "Student deleted successfully";
        }
    }
}


