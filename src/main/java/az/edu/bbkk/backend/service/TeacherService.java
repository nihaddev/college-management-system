package az.edu.bbkk.backend.service;


import az.edu.bbkk.backend.dto.LoginRequestDto;
import az.edu.bbkk.backend.entity.Student;
import az.edu.bbkk.backend.entity.Teacher;
import az.edu.bbkk.backend.repositories.TeacherRepository;
import az.edu.bbkk.backend.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@Validated
public class TeacherService extends BaseService {
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public TeacherService(TeacherRepository teacherRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.teacherRepository = teacherRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    // Register a Teacher
    public Teacher createTeacher(@Valid Teacher regs){
        String encodePassword = passwordEncoder.encode(regs.getPassword());
        regs.setPassword(encodePassword);
        return teacherRepository.save(regs);
    }

    public String loginTeacherWithUsername(@Valid LoginRequestDto loginBody) {
        Teacher findUser = teacherRepository.findByUsername(loginBody.getUsername())
                .orElseThrow(() -> new RuntimeException("Bu Username ilə muellim tapılmadı!"));

        if (!passwordEncoder.matches(loginBody.getPassword(), findUser.getPassword())) {
            throw new RuntimeException("Şifrə yanlışdır!");
        }

        return jwtUtil.generateTeacherToken(findUser);
    }
    public String loginTeacherWithFinCode(@Valid LoginRequestDto loginBody) {
        Teacher findUser = teacherRepository.findByFinCode(loginBody.getFinCode())
                .orElseThrow(() -> new RuntimeException("Bu fin code ilə muellim tapılmadı!"));

        if (!passwordEncoder.matches(loginBody.getPassword(), findUser.getPassword())) {
            throw new RuntimeException("Şifrə yanlışdır!");
        }

        return jwtUtil.generateTeacherToken(findUser);
    }


}
