package az.edu.bbkk.backend.controller;

import az.edu.bbkk.backend.entity.Teacher;
import az.edu.bbkk.backend.service.StudentService;
import az.edu.bbkk.backend.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final StudentService studentService;
    private final TeacherService teacherService;

    public AdminController(StudentService studentService, TeacherService teacherService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    // Teacher
    @PostMapping("/teacher/new")
    public ResponseEntity<?> newTeacher(@Valid @RequestBody Teacher teacher) {
        Teacher newTeacherResponse = teacherService.createTeacher(teacher);
        return ResponseEntity.ok(Map.of(
                "status", 200,
                "data", newTeacherResponse
        ));
    }
}
