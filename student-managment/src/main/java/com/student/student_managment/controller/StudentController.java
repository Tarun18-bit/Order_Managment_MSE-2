package com.student.student_managment.controller;

import com.student.student_managment.entity.Student;
import com.student.student_managment.service.StudentService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService service;
    public StudentController(StudentService service) { this.service = service; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student create(@Valid @RequestBody Student student) { return service.create(student); }

    @GetMapping
    public List<Student> list() { return service.findAll(); }

    @GetMapping("/search")
    public List<Student> search(@RequestParam(required = false) String email,
                                @RequestParam(required = false) String course) {
        if (email != null && !email.isBlank()) return service.findByEmail(email);
        if (course != null && !course.isBlank()) return service.findByCourse(course);
        return service.findAll();
    }

    @GetMapping("/merit-list")
    public List<Student> meritList() { return service.meritList(); }

    @GetMapping("/same-skills")
    public Map<String, List<Student>> studentsSharingSkills() { return service.studentsSharingSkills(); }
    @GetMapping("/common-skills")
    public Set<String> commonSkills() { return service.commonSkills(); }

    @GetMapping("/programming-below-50-maths-at-least-75")
    public List<Student> lowProgrammingStrongMath() { return service.findLowProgrammingStrongMath(); }

    @GetMapping("/{id}")
    public Student get(@PathVariable Long id) { return service.findById(id); }

    @PutMapping("/{id}/scores-skills")
    public Student updateScoresAndSkills(@PathVariable Long id, @Valid @RequestBody UpdateScoresSkillsRequest changes) {
        return service.updateScoresAndSkills(id, changes);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}


