package az.edu.bbkk.backend.repositories;

import az.edu.bbkk.backend.entity.Seminar;
import az.edu.bbkk.backend.entity.StudentSeminars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeminarRepository extends JpaRepository<Seminar,Long> {
    Optional<Seminar> findByUsername(String username);
    Optional<Seminar> findByGroupId(String groupId);
    Optional<Seminar> findBySeminarId(String seminarId);
    Optional<Seminar> findByFaculty(String faculty);
}
