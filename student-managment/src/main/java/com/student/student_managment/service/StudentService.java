package com.student.student_managment.service;

import com.student.student_managment.entity.Student;
import com.student.student_managment.controller.UpdateScoresSkillsRequest;
import com.student.student_managment.repository.StudentRepository;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) { this.repository = repository; }

    public Student create(Student student) {
        if (repository.existsById(student.getStudentId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A student with this ID already exists");
        }
        if (!repository.findByEmailIgnoreCase(student.getEmail()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A student with this email already exists");
        }
        return repository.save(student);
    }

    public List<Student> findAll() { return repository.findAll(); }
    public Student findById(Long id) { return repository.findById(id).orElseThrow(() -> notFound(id)); }
    public List<Student> findByEmail(String email) { return repository.findByEmailIgnoreCase(email); }
    public List<Student> findByCourse(String course) { return repository.findByCourseNameContainingIgnoreCase(course); }
    public List<Student> meritList() { return repository.findAll().stream().sorted(Comparator.comparingDouble(Student::calculateAverage).reversed()).toList(); }
    public List<Student> findLowProgrammingStrongMath() { return repository.findByProgrammingScoreLessThanAndMathematicsScoreGreaterThanEqual(50, 75); }

    public Student updateScoresAndSkills(Long id, UpdateScoresSkillsRequest changes) {
        Student student = findById(id);
        student.setProgrammingScore(changes.programmingScore());
        student.setMathematicsScore(changes.mathematicsScore());
        student.setTechnicalSkills(changes.technicalSkills());
        return repository.save(student);
    }

    public void delete(Long id) { repository.delete(findById(id)); }

    public Map<String, List<Student>> studentsSharingSkills() {
        Map<String, List<Student>> studentsBySkill = new HashMap<>();
        for (Student student : repository.findAll()) {
            for (String skill : student.getTechnicalSkills()) {
                studentsBySkill.computeIfAbsent(skill.trim().toLowerCase(), key -> new ArrayList<>()).add(student);
            }
        }
        return studentsBySkill.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    public Set<String> commonSkills() {
        List<Student> students = repository.findAll();
        if (students.isEmpty()) return Set.of();
        Set<String> common = new LinkedHashSet<>(students.get(0).getTechnicalSkills());
        for (int i = 1; i < students.size(); i++) common.retainAll(students.get(i).getTechnicalSkills());
        return common;
    }

    private ResponseStatusException notFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Student " + id + " was not found");
    }
}






