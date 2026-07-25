package az.edu.bbkk.backend.controller;

import az.edu.bbkk.backend.dto.LoginDto;
import az.edu.bbkk.backend.dto.RegisterDto;
import az.edu.bbkk.backend.entity.Student;
import az.edu.bbkk.backend.service.StudentService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final StudentService studentService;


    public AuthController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> LoginWithUsername(@Valid @RequestBody Student loginbody, HttpServletResponse response) {
       // return studentService.loginStudentWithUsername(loginbody);
        String token = studentService.loginStudentWithUsername(loginbody);

        setAuthCookie(response, token);
        return ResponseEntity.ok(Map.of("message", "Uğurla daxil oldunuz."));

    }

    @PostMapping("/login/withfincode")
    public ResponseEntity<?> LoginWithFinCode(@Valid @RequestBody Student loginbody, HttpServletResponse response) {
      // return studentService.loginStudentWithFin(loginbody);
        String token = studentService.loginStudentWithFin(loginbody);
        setAuthCookie(response, token);
        return ResponseEntity.ok(Map.of("message", "Uğurla daxil oldunuz."));
    }

    @PostMapping("/register")
    public Student registerUser(@Valid @RequestBody Student regs) {
        return studentService.registerStudent(regs);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal Student student) {
        if (student == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "İstifadəçi tapılmadı və ya token keçərsizdir."));
        }

        return ResponseEntity.ok(student);
    }

    private void setAuthCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("bbkk-auth", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(86400); // 1 gün
        response.addCookie(cookie);
    }
    // BUNU OYRENECEM !
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

}
