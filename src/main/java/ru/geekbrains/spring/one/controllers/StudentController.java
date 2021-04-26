package ru.geekbrains.spring.one.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.one.model.Student;
import ru.geekbrains.spring.one.services.StudentService;

import java.util.List;
import java.util.Optional;

@Controller
public class StudentController {
    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // [http://localhost:8189/app]/
    @GetMapping("/")
    public String showAllStudentsPage(Model model) {
        List<Student> students = studentService.findAll();
        model.addAttribute("students", students);
        return "index";
    }

    @GetMapping("/students/find/{id}")
    public String showStudentInfo(@RequestParam Long id, Model model) {
        Optional<Student> student = studentService.findOneById(id);
        if (student.isPresent()) {
            model.addAttribute("student", student.get());
        }
        return "student_info";
    }

    @GetMapping("/students/finder")
    public String showFinder() {
        return "find_student_form";
    }

    // [http://localhost:8189/app]/students/creator
    @GetMapping("/students/create")
    public String showCreator() {
        return "create_student_form";
    }

//    [http://localhost:8189/app]/students/create?id=10&name=Nicolas&score=95
//    @PostMapping("/create")
//    public String createNewStudent(@RequestParam Long id, @RequestParam String name, @RequestParam int score) {
//        Student student = new Student(id, name, score);
//        studentService.save(student);
//        return "redirect:/students/all";
//    }

    @PostMapping("/students/create")
    public String createNewStudent(@ModelAttribute Student student) {
        studentService.save(student);
        return "redirect:/";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudentById(@PathVariable Long id) {
        studentService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/students/minus/{id}")
    public String minusStudentScore(@PathVariable Long id) {
        studentService.scoreDecrement (id);
        return "redirect:/";
    }

    @GetMapping("/students/plus/{id}")
    public String plusStudentScore(@PathVariable Long id) {
        studentService.scoreIncrement (id);
        return "redirect:/";
    }
}
