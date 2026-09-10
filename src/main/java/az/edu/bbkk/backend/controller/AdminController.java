package az.edu.bbkk.backend.controller;

import az.edu.bbkk.backend.entity.Teacher;
import az.edu.bbkk.backend.service.AdminService;
import az.edu.bbkk.backend.service.StudentService;
import az.edu.bbkk.backend.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    private final AdminService adminService;

    public AdminController(StudentService studentService, TeacherService teacherService, AdminService adminService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.adminService = adminService;
    }

    // Teacher
    @PostMapping("/teacher/new")
    public ResponseEntity<?> newTeacher(@Valid @RequestBody Teacher teacher) {
        if (!adminService.isAdmin()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Unauthorized access. Bu əməliyyat üçün admin hüququ tələb olunur."));
        }
        String requiredPerm = "dean";
        if (!adminService.checkPermLevel(requiredPerm)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Unauthorized access. Bu əməliyyat üçün dekan və ya daha üst hüquq tələb olunur."));
        }
        Teacher newTeacherResponse = teacherService.createTeacher(teacher);
        return ResponseEntity.ok(Map.of(
                "status", 200,
                "data", newTeacherResponse
        ));
    }
}
