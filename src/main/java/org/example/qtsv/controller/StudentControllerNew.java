package org.example.qtsv.controller;

import org.example.qtsv.api.ApiResponse;
import org.example.qtsv.api.LastYearStudentDataResponse;
import org.example.qtsv.entity.LastYearStudentEntity;
import org.example.qtsv.entity.Student;
import org.example.qtsv.api.StudentDataResponse;
import org.example.qtsv.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/students")
public class StudentControllerNew {
    @Autowired
    @Qualifier("StudentServiceOld")
    private StudentService service;


    private static final int FILTER_BY_LAST_YEAR = 1;
    private static final int SORT_BY_LAST_YEAR_AND_COUNTRY = 2;
    private static final int SORT_BY_GPA_HIGH_TO_LOW = 3;
    private static final int SORT_BY_GPA_HIGH_TO_LOW_AND_LAST_NAME = 4;


    @GetMapping("/")
    public ApiResponse<StudentDataResponse> showStudents(@RequestParam(value = "sort", required = false) Integer sort,
                                                         @RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                                         @ModelAttribute Student s) {
        Pageable pageable = PageRequest.of(page, size);
        List<Student> studentList = new ArrayList<Student>();
        //service.showUser(user);

        if (s.getFirstName() != null || s.getLastName() != null ||  s.getAge() != 0 ||
                s.getStudentCode() != null || s.getYear() != 0 || s.getMajor() !=null || s.getCountry() != null) {
            studentList = service.search(s);
        }

        if (sort != null){
            if(sort == FILTER_BY_LAST_YEAR){
                studentList = service.sortByYearOfStudy(studentList);
            }
            if(sort == SORT_BY_LAST_YEAR_AND_COUNTRY){
                studentList = service.sortByCountryFirstAlphabet(studentList);
            }
            if(sort == SORT_BY_GPA_HIGH_TO_LOW){
                studentList = service.sortByGPAFromHighToLow(studentList);
            }
            if(sort == SORT_BY_GPA_HIGH_TO_LOW_AND_LAST_NAME){
                studentList = service.sortByGPAFromHighToLowAndLastNameFirstAlphabet(studentList);
            }
        }
        studentList = service.sortByLastName(studentList);
        Page<Student> students = service.getPage(studentList,pageable);
        return new ApiResponse<>(true, "Thực hiện thành công", service.getContent(students));
    }


    @GetMapping("/thesis/")
    public ApiResponse<LastYearStudentDataResponse> showStudents(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                 @RequestParam(value = "size", defaultValue = "10") int size,
                                                                 @RequestParam(value = "thesisTitle") String thesisTitle) {
        Pageable pageable = PageRequest.of(page, size);
        List<LastYearStudentEntity> lastYearStudentList = new ArrayList<>();
        if (thesisTitle != null){
            lastYearStudentList = service.search(thesisTitle);
        }
        Page<LastYearStudentEntity> students = service.getThesisPage(lastYearStudentList,pageable);
        return new ApiResponse<>(true, "Thực hiện thành công", service.getThesis(students));
    }




    @PostMapping("/add")
    public String addStudent(@RequestBody Student student) {
        service.validateInput(student, true);
        service.save(student);
        return "Student saved successfully";
    }

    @PostMapping("/addLastYearStudentThesis/{id}")
    public String addLastYearStudentThesis(@PathVariable(name = "id") int id, @RequestBody LastYearStudentEntity student ){
        service.save(id, student);
        return "Student thesis register successful.";
    }

    @PutMapping("/edit/{id}")
    public String editStudent(@PathVariable(name = "id") int id, @RequestBody Student updatedStudent) {
        service.validateInput(updatedStudent, false);
        service.saveEdit(id, updatedStudent);
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


