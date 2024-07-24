package org.example.qtsv.controller;

import org.example.qtsv.entity.Student;
import org.example.qtsv.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class StudentController {
    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @RequestMapping("/")
    public String viewHomePage(Model model) {
        List<Student> listStudents = service.listAll();
        model.addAttribute("listStudents", listStudents);
        return "index";
    }

    @RequestMapping("/addStudent")
    public String showNewStudentPage(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "addStudent";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveStudent(@ModelAttribute("student") Student student, Model model) {
        String errorMessage = service.validateNewInformation(student);
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            return "addStudent";
        } else {
            service.save(student);
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/saveEdit", method = RequestMethod.POST)
    public String saveEditStudent(@ModelAttribute("student") Student updatedStudent, RedirectAttributes model) {
        String errorMessage = service.validateEditInformation(updatedStudent);
        if (errorMessage != null) {
            model.addFlashAttribute("errorMessage", errorMessage);
            int id = updatedStudent.getId();
            return "redirect:/edit/" + id;
        } else {
            Student existingStudent = new Student();
            existingStudent.setId(updatedStudent.getId());
            existingStudent.setFirstName(updatedStudent.getFirstName());
            existingStudent.setLastName(updatedStudent.getLastName());
            existingStudent.setAge(updatedStudent.getAge());
            existingStudent.setStudentCode(updatedStudent.getStudentCode());
            existingStudent.setDepartment(updatedStudent.getDepartment());
            existingStudent.setMajor(updatedStudent.getMajor());
            existingStudent.setCountry(updatedStudent.getCountry());
            existingStudent.setGpa(updatedStudent.getGpa());
            service.save(existingStudent);
            return "redirect:/";
        }
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditStudentPage(@PathVariable(name = "id") long id) {
        ModelAndView mav = new ModelAndView("editStudent");
        Student student = service.get(id);
        mav.addObject("student", student);

        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteStudent(@PathVariable(name = "id") long id) {
        service.delete(id);
        return "redirect:/";
    }

}
