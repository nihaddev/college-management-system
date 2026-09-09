package az.edu.bbkk.backend.repositories;

import az.edu.bbkk.backend.entity.Seminar;
import az.edu.bbkk.backend.entity.StudentSeminars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeminarRepository extends JpaRepository<Seminar,Long> {
    Optional<Seminar> findByGroupId(String groupId);
    Optional<Seminar> findBySeminarId(String seminarId);
    Optional<Seminar> findByFaculty(String faculty);
    @Query(value = "SELECT s.* FROM seminars s " +
            "INNER JOIN student_seminars ss ON ss.seminar_id = s.seminar_id " +
            "WHERE ss.student_id = :studentId " +
            "AND to_timestamp(s.seminar_end_date::bigint) > CURRENT_TIMESTAMP",
            nativeQuery = true)
    List<Seminar> findActiveSeminarsByStudentId(@Param("studentId") String studentId);
}
