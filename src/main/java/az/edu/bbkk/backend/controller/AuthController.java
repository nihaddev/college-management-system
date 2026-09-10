package az.edu.bbkk.backend.controller;

import az.edu.bbkk.backend.dto.LoginRequestDto;
import az.edu.bbkk.backend.entity.Student;
import az.edu.bbkk.backend.entity.Teacher;
import az.edu.bbkk.backend.service.StudentService;
import az.edu.bbkk.backend.service.TeacherService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final StudentService studentService;
    private final TeacherService teacherService;

    public AuthController(StudentService studentService, TeacherService teacherService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> LoginWithUsername(
            @Validated(LoginRequestDto.UsernameGroup.class) @RequestBody LoginRequestDto loginbody,
            HttpServletResponse response) {

        String token = null;

        try {

            token = studentService.loginStudentWithUsername(loginbody);
        } catch (Exception studentEx) {
            try {

                token = teacherService.loginTeacherWithUsername(loginbody);
            } catch (Exception teacherEx) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "İstifadəçi adı və ya şifrə yanlışdır."));
            }
        }

        setAuthCookie(response, token);
        return ResponseEntity.ok(Map.of("message", "Uğurla daxil oldunuz."));
    }

    @PostMapping("/login/withfincode")
    public ResponseEntity<?> LoginWithFinCode(
            @Validated(LoginRequestDto.FinCodeGroup.class) @RequestBody LoginRequestDto loginbody,
            HttpServletResponse response) {

        String token = null;

        try {

            token = studentService.loginStudentWithFin(loginbody);
        } catch (Exception studentEx) {
            try {

                token = teacherService.loginTeacherWithFinCode(loginbody);
            } catch (Exception teacherEx) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "FIN kod və ya şifrə yanlışdır."));
            }
        }

        setAuthCookie(response, token);
        return ResponseEntity.ok(Map.of("message", "Uğurla daxil oldunuz."));
    }

    @PostMapping("/register")
    public Student registerUser(@Valid @RequestBody Student regs) {
        return studentService.registerStudent(regs);
    }

  /*  @GetMapping("/me")
    public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal Student student) {
        if (student == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "İstifadəçi tapılmadı və ya token keçərsizdir."));
        }

        return ResponseEntity.ok(student);
    }*/

    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal Object principal) {
        if (principal == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "İstifadəçi tapılmadı və ya token keçərsizdir."));
        }

        Map<String, Object> userData = new HashMap<>();

        if (principal instanceof Student student) {
            userData.put("id", student.getId());
            userData.put("username", student.getUsername());
            userData.put("name", student.getName());
            userData.put("role", "STUDENT");
        } else if (principal instanceof Teacher teacher) {
            userData.put("id", teacher.getId());
            userData.put("username", teacher.getUsername());
            userData.put("name", teacher.getName());
            userData.put("role", "TEACHER");
        }

        return ResponseEntity.ok(Map.of("status", 200, "data", userData));
    }

    private void setAuthCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("bbkk-auth", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(86400); // 1 gün
        response.addCookie(cookie);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}