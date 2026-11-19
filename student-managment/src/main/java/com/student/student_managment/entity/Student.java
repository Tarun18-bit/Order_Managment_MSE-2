package com.student.student_managment.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotBlank(message = "Student name is required")
    private String studentName;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Course name is required")
    private String courseName;

    @NotNull(message = "Programming score is required")
    @DecimalMin(value = "0.0", message = "Score cannot be below 0")
    @DecimalMax(value = "100.0", message = "Score cannot exceed 100")
    private Double programmingScore;

    @NotNull(message = "Mathematics score is required")
    @DecimalMin(value = "0.0", message = "Score cannot be below 0")
    @DecimalMax(value = "100.0", message = "Score cannot exceed 100")
    private Double mathematicsScore;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "student_skills", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "skill")
    private List<String> technicalSkills = new ArrayList<>();

    public Student() { }

    public Student(Long studentId, String studentName, String email, String courseName,
                   Double programmingScore, Double mathematicsScore, List<String> technicalSkills) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.email = email;
        this.courseName = courseName;
        this.programmingScore = programmingScore;
        this.mathematicsScore = mathematicsScore;
        setTechnicalSkills(technicalSkills);
    }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public Double getProgrammingScore() { return programmingScore; }
    public void setProgrammingScore(Double programmingScore) { this.programmingScore = programmingScore; }
    public Double getMathematicsScore() { return mathematicsScore; }
    public void setMathematicsScore(Double mathematicsScore) { this.mathematicsScore = mathematicsScore; }
    public List<String> getTechnicalSkills() { return technicalSkills; }
    public void setTechnicalSkills(List<String> technicalSkills) {
        this.technicalSkills = technicalSkills == null ? new ArrayList<>() : new ArrayList<>(technicalSkills);
    }

    @JsonProperty("average")
    public double calculateAverage() {
        if (programmingScore == null || mathematicsScore == null) return 0.0;
        return (programmingScore + mathematicsScore) / 2.0;
    }

    @JsonProperty("strongerSubject")
    public String getStrongerSubject() {
        if (programmingScore == null || mathematicsScore == null) return "Not available";
        int comparison = Double.compare(programmingScore, mathematicsScore);
        return comparison > 0 ? "Programming" : comparison < 0 ? "Mathematics" : "Both are equal";
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", email='" + email + '\'' +
                ", courseName='" + courseName + '\'' +
                ", programmingScore=" + programmingScore +
                ", mathematicsScore=" + mathematicsScore +
                ", technicalSkills=" + technicalSkills +
                '}';
    }
    @JsonProperty("performanceCategory")
    public String getPerformanceCategory() {
        double average = calculateAverage();
        if (average >= 85) return "Excellent";
        if (average >= 70) return "Good";
        if (average >= 50) return "Satisfactory";
        return "Needs Improvement";
    }
}



