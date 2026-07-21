package az.edu.bbkk.backend.repositories;

import az.edu.bbkk.backend.entity.Seminar;
import az.edu.bbkk.backend.entity.StudentSeminars;
import az.edu.bbkk.backend.entity.groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupsRepository extends JpaRepository<groups,Long> {
    Optional<groups> findByGroupId(String groupId);
    Optional<groups> findByStarostaId(String starostaId);
    Optional<groups> findByFaculty(String faculty);


}
