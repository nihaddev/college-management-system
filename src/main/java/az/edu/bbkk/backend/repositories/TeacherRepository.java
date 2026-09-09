package az.edu.bbkk.backend.repositories;


import az.edu.bbkk.backend.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    Optional<Teacher> findByFinCode(String finCode);
    Optional<Teacher> findByUsername(String username);
    Optional<Teacher> findByEmail(String email);
    Optional<Teacher> findById(Long id);
    Optional<Teacher> findByUsernameAndEmail(String username, String email);
    Optional<Teacher> findByEmailAndUsername(String email, String username);
    //Optional<Teacher> findByTeacherId(Long id);


}
