package org.example.qtsv.controller;

import org.example.qtsv.ApiResponse;
import org.example.qtsv.entity.Student;
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
    private static final int SORT_BY_LAST_YEAR = 1;
    private static final int SORT_BY_LAST_YEAR_AND_COUNTRY = 2;
    private static final int SORT_BY_GPA_HIGH_TO_LOW = 3;
    private static final int SORT_BY_GPA_HIGH_TO_LOW_AND_LAST_NAME = 4;

    @Autowired
    public StudentControllerNew(StudentService service) {
        this.service = service;
    }


    @GetMapping("/")
    public ApiResponse<Map<String, Object>> showAllStudents(@RequestParam(value = "sort", required = false) Integer sort,@RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "size", defaultValue = "10") int size, @ModelAttribute Student s) {
        Pageable pageable = PageRequest.of(page, size);
        List<Student> studentList = new ArrayList<Student>();
        List<Student> allStudents = service.listAll();
        studentList = service.sortByLastName(allStudents);

        //Magic Number
        // Request Life Cycle
        if (sort != null){
            if(sort == SORT_BY_LAST_YEAR){
                studentList = service.sortByLastYear(allStudents);
            }
            if(sort == SORT_BY_LAST_YEAR_AND_COUNTRY){
                studentList = service.sortByLastYearAndCountry(allStudents);
            }
            if(sort == SORT_BY_GPA_HIGH_TO_LOW){
                studentList = service.sortByGPAFromHighToLow(allStudents);
            }
            if(sort == SORT_BY_GPA_HIGH_TO_LOW_AND_LAST_NAME){
                studentList = service.sortByGPAFromHighToLowAndLastName(allStudents);
            }
        }


        if (s.getFirstName() != null || s.getLastName() != null ||  Integer.valueOf(s.getAge()) != 0 ||
                s.getStudentCode() != null || Integer.valueOf(s.getYear()) != 0 || s.getMajor() !=null || s.getCountry() != null) {
            studentList = service.search(s, allStudents);
        }
        Page<Student> students = service.getPage(studentList,pageable);
        return new ApiResponse<>(true, "Thực hiện thành công", service.getContent(students));
    }




    @PostMapping("/add")
    public String addStudent(@RequestBody Student student) {
        String errorMessage = service.validateNewInformation(student);
        if (errorMessage != null) {
            return errorMessage;
        } else {
            service.save(student);
            return "Student saved successfully";
        }
    }

    @PutMapping("/edit/{id}")
    public String editStudent(@PathVariable(name = "id") int id, @RequestBody Student updatedStudent) {
        Student existingStudent = service.get(id);
        String errorMessage = service.validateEditInformation(updatedStudent);
        if (errorMessage != null) {
            return errorMessage;
        } else {
            if (existingStudent != null) {
                service.saveEdit(id, updatedStudent);
                return "Student updated successfully";
            } else {
                return "Student not found";
            }
        }
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


