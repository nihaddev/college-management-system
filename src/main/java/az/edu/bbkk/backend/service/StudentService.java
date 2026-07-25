package az.edu.bbkk.backend.service;

import az.edu.bbkk.backend.entity.Student;
import az.edu.bbkk.backend.entity.StudentSeminars;
import az.edu.bbkk.backend.entity.groups;
import az.edu.bbkk.backend.repositories.GroupsRepository;
import az.edu.bbkk.backend.repositories.StudentRepository;
import az.edu.bbkk.backend.repositories.StudentSeminarRepository;
import az.edu.bbkk.backend.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentSeminarRepository studentSeminarRepository;
    private final GroupsRepository groupsRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public StudentService(StudentRepository studentRepository, StudentSeminarRepository studentSeminarRepository, GroupsRepository groupsRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.studentRepository = studentRepository;
        this.studentSeminarRepository = studentSeminarRepository;
        this.groupsRepository = groupsRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public  Student registerStudent(@Valid Student regs) {
        System.out.println("Gələn xam şifrə: " + regs.getPassword());
        String encodePassword = passwordEncoder.encode(regs.getPassword());
        regs.setPassword(encodePassword);
        return studentRepository.save(regs);
    }

    public String loginStudentWithUsername(@Valid Student loginBody) {
        Student findUser = studentRepository.findByUsername(loginBody.getUsername())
                .orElseThrow(() -> new RuntimeException("Bu Usernameilə tələbə tapılmadı!"));

        if (!passwordEncoder.matches(loginBody.getPassword(), findUser.getPassword())) {
            throw new RuntimeException("Şifrə yanlışdır!");
        }

        return jwtUtil.generateToken(findUser);
    }

    public String loginStudentWithFin(@Valid Student loginBody) {
        Student findUser = studentRepository.findByFinCode(loginBody.getFinCode())
                .orElseThrow(() -> new RuntimeException("Bu FİN kod ilə tələbə tapılmadı!"));

        if (!passwordEncoder.matches(loginBody.getPassword(), findUser.getPassword())) {
            throw new RuntimeException("Şifrə yanlışdır!");
        }

        return jwtUtil.generateToken(findUser);
    }

    public Student getStudentDetails(String id) {
        return studentRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Tələbə tapılmadı!"));
    }

    public Object getStudentGroup(String studentid){
        String studentGroupId = studentRepository.findById(Long.valueOf(studentid))
                .map(Student::getGroupId)
                .orElseThrow(() -> new RuntimeException("Tələbə tapılmadı!"));

        Object studentGroup = groupsRepository.findByGroupId(studentGroupId)
                .orElseThrow(() -> new RuntimeException("Qrup tapılmadı!"));

        return studentGroup;
    }

    public groups getStudentGroupById(String studentid, String groupid) {
        String studentGroupId = studentRepository.findById(Long.valueOf(studentid))
                .map(Student::getGroupId)
                .orElseThrow(() -> new RuntimeException("Tələbə tapılmadı!"));

        if (!studentGroupId.equals(groupid)) {
            throw new RuntimeException("İcazə verilmədi: Bu tələbə qeyd olunan qrupun üzvü deyil!");
        }

        return groupsRepository.findByGroupId(groupid)
                .orElseThrow(() -> new RuntimeException("Qrup tapılmadı!"));
    }

    public StudentSeminars getStudentSeminarsWithGroupId(String studentid, String groupid) {
        String studentGroupId = studentRepository.findById(Long.valueOf(studentid))
                .map(Student::getGroupId)
                .orElseThrow(() -> new RuntimeException("Tələbə tapılmadı!"));

        if (!studentGroupId.equals(groupid)) {
            throw new RuntimeException("İcazə verilmədi: Bu tələbə qeyd olunan qrupun üzvü deyil!");
        }
        groups getgroup = groupsRepository.findByGroupId(groupid)
                .orElseThrow(() -> new RuntimeException("Qrup tapılmadı!"));

        return studentSeminarRepository.findByGroupId(groupid)
                .orElseThrow(() -> new RuntimeException("Qrup ucun Seminar tapılmadı!"));
    }


}
