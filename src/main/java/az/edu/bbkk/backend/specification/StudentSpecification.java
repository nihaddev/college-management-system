package az.edu.bbkk.backend.specification;

import az.edu.bbkk.backend.entity.Student;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class StudentSpecification {

    public static Specification<Student> filterStudents(String finCode, String username, String name, String surname) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (finCode != null && !finCode.isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("finCode"), finCode));
            }
            if (username != null && !username.isBlank()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), "%" + username.toLowerCase() + "%"));
            }
            if (name != null && !name.isBlank()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (surname != null && !surname.isBlank()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("surname")), "%" + surname.toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}