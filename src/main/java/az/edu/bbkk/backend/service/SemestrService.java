package az.edu.bbkk.backend.service;


import az.edu.bbkk.backend.repositories.SeminarRepository;
import az.edu.bbkk.backend.repositories.StudentRepository;
import az.edu.bbkk.backend.repositories.StudentSeminarRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@Transactional
public class SemestrService extends BaseService {

    private final StudentRepository studentRepository;

    private final StudentSeminarRepository studentSeminarRepository;
    private final SeminarRepository seminarRepository;


    public SemestrService(StudentRepository studentRepository, StudentSeminarRepository studentSeminarRepository, SeminarRepository seminarRepository) {
        this.studentRepository = studentRepository;
        this.studentSeminarRepository = studentSeminarRepository;
        this.seminarRepository = seminarRepository;
    }

}
