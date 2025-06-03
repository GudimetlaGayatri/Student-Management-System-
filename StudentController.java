package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService service;

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "5") int size,
                       @RequestParam(required = false) String keyword,
                       Model model) {
        Page<Student> students = service.getAll(keyword, page, size);
        model.addAttribute("students", students);
        model.addAttribute("keyword", keyword);
        return "students";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("student", new Student());
        return "form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute Student student, BindingResult result) {
        if (result.hasErrors()) return "form";
        service.save(student);
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        return service.getById(id).map(s -> {
            model.addAttribute("student", s);
            return "form";
        }).orElse("redirect:/students");
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/students";
    }
}

