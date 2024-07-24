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

    @RequestMapping(value = "/sort", method = RequestMethod.POST)
    public String sortStudent(@RequestParam("choice") int choice ,Model model){
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
            System.out.println("Default");
        }
        model.addAttribute("listStudents", sortStudents);
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
    public String saveEditStudent(@ModelAttribute("student") Student student, RedirectAttributes model) {
        String errorMessage = service.validateEditInformation(student);
        if (errorMessage != null) {
            model.addFlashAttribute("errorMessage", errorMessage);
            long id = student.getId();
            return "redirect:/edit/" + id;
        } else {
            service.save(student);
            return "redirect:/";
        }
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditStudentPage(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("editStudent");
        Student student = service.get(id);
        mav.addObject("student", student);

        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteStudent(@PathVariable(name = "id") int id) {
        service.delete(id);
        return "redirect:/";
    }
}
