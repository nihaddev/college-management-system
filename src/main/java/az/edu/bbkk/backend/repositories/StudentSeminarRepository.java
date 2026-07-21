package az.edu.bbkk.backend.repositories;

import az.edu.bbkk.backend.entity.Seminar;
import az.edu.bbkk.backend.entity.StudentSeminars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentSeminarRepository extends JpaRepository<StudentSeminars,Long> {
    Optional<StudentSeminars> findByFinCode(String finCode);
    Optional<StudentSeminars> findByUsername(String username);
    Optional<StudentSeminars> findByGroupId(String groupId);
    Optional<StudentSeminars> findBySeminarId(String seminarId);

}
