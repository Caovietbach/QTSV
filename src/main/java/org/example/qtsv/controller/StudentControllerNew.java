package org.example.qtsv.controller;

import org.example.qtsv.entity.Student;
import org.example.qtsv.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/students")
public class StudentControllerNew {
    private final StudentService service;

    @Autowired
    public StudentControllerNew(StudentService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<Student> showAllStudents(@RequestParam(value = "choice",required = false) Integer choice, @RequestParam(value = "firstName", required = false) String firstName,
                                         @RequestParam(value = "lastName", required = false) String lastName,  @RequestParam(value = "age", required = false) Integer age,
                                         @RequestParam(value = "studentCode", required = false) String studentCode, @RequestParam(value = "year", required = false) Integer year,
                                         @RequestParam(value = "major", required = false) String major, @RequestParam(value = "country", required = false) String country){
        List<Student> sortStudents = new ArrayList<Student>();
        sortStudents = service.listAll();
        if (choice != null){
            if(choice == 1){
                sortStudents = service.sortByLastYear();
            }
            if(choice == 2){
                sortStudents = service.sortByLastYearAndCountry();
            }
            if(choice == 3){
                sortStudents = service.sortByGPAFromHighToLow();
            }
            if(choice == 4){
                sortStudents = service.sortByGPAFromHighToLowAndLastName();
            }
        }
        if (firstName != null || lastName != null || age != null || studentCode != null || year != null || major != null || country != null){
            sortStudents = service.search(firstName, lastName, age, studentCode, year, major, country);
        }
        return sortStudents;
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
                existingStudent.setFirstName(updatedStudent.getFirstName());
                existingStudent.setLastName(updatedStudent.getLastName());
                existingStudent.setAge(updatedStudent.getAge());
                existingStudent.setStudentCode(updatedStudent.getStudentCode());
                existingStudent.setDepartment(updatedStudent.getDepartment());
                existingStudent.setMajor(updatedStudent.getMajor());
                existingStudent.setYear(updatedStudent.getYear());
                existingStudent.setCountry(updatedStudent.getCountry());
                existingStudent.setGpa(updatedStudent.getGpa());
                service.save(existingStudent);
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


