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
    public List<Student> viewHomePage() {
        return service.listAll();
    }

    @PostMapping("/sort")
    public List<Student> sortStudent(@RequestParam("choice") int choice) {
        List<Student> sortStudents = new ArrayList<Student>();
        if(choice == 1){
            sortStudents = service.sortByLastYear();
        } else if(choice == 2){
            sortStudents = service.sortByLastYearAndCountry();
        } else if(choice == 3){
            sortStudents = service.sortByGPAFromHighToLow();
        } else if(choice == 4){
            sortStudents = service.sortByGPAFromHighToLowAndLastName();
        } else {
            sortStudents = null;
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
                existingStudent.setCountry(updatedStudent.getCountry());
                existingStudent.setGpa(updatedStudent.getGpa());
                service.save(existingStudent);
                return "Student updated successfully";
            } else {
                return "Student not found";
            }
        }


    }



    @GetMapping("/edit/{id}")
    public Student getStudent(@PathVariable(name = "id") int id) {
        return service.get(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteStudent(@PathVariable(name = "id") int id) {
        service.delete(id);
        return "Student deleted successfully";
    }
}


