package az.edu.bbkk.backend.repositories;

import az.edu.bbkk.backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long>, JpaSpecificationExecutor<Student> {
    Optional<Student> findByFinCode(String finCode);
    Optional<Student> findById(String id);
    Optional<Student> findByUsername(String username);
}
