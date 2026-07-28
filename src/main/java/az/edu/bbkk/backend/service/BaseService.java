package az.edu.bbkk.backend.service;

import az.edu.bbkk.backend.entity.Student;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseService {
    protected Student getCurrentStudent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        return (Student) authentication.getPrincipal();
    }


    protected Long getCurrentStudentId() {
        return getCurrentStudent().getId();
    }
}
