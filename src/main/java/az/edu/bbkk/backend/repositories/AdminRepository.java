package az.edu.bbkk.backend.repositories;

import az.edu.bbkk.backend.entity.Admins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admins,Long> {
    Optional<Admins> findByFinCode(String finCode);
}
