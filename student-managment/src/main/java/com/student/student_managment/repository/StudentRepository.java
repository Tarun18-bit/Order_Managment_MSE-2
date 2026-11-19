package com.student.student_managment.repository;

import com.student.student_managment.entity.Student;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByEmailIgnoreCase(String email);
    List<Student> findByCourseNameContainingIgnoreCase(String courseName);
    List<Student> findAllByOrderByProgrammingScoreDescMathematicsScoreDesc();
    List<Student> findByProgrammingScoreLessThanAndMathematicsScoreGreaterThanEqual(double programmingScore, double mathematicsScore);
}
