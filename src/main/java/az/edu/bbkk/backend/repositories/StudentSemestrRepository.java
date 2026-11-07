package az.edu.bbkk.backend.repositories;

import az.edu.bbkk.backend.entity.StudentSemestr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentSemestrRepository extends JpaRepository<StudentSemestr, Long> {
    Optional<StudentSemestr> findBySemestrId(String semestrId);
    Optional<StudentSemestr> findByStudentId(Long studentId);
    Optional<StudentSemestr> findByGpa(Number gpa);
    Optional<StudentSemestr> findBySemestrStartDate(String semestrStartDate);
}
