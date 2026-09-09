package az.edu.bbkk.backend.service;


import az.edu.bbkk.backend.entity.Seminar;
import az.edu.bbkk.backend.entity.StudentSeminars;
import az.edu.bbkk.backend.repositories.SeminarRepository;
import az.edu.bbkk.backend.repositories.StudentRepository;
import az.edu.bbkk.backend.repositories.StudentSeminarRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class SeminarService extends BaseService {
    private final StudentRepository studentRepository;
    private final StudentSeminarRepository studentSeminarRepository;
    private final SeminarRepository seminarRepository;

    public SeminarService(StudentRepository studentRepository, StudentSeminarRepository studentSeminarRepository, SeminarRepository seminarRepository) {
        this.studentRepository = studentRepository;
        this.studentSeminarRepository = studentSeminarRepository;
        this.seminarRepository = seminarRepository;
    }


    public List<Seminar> getStudentAllActiveSemminars() {
        Long currentUserId = getCurrentStudentId();
        List<StudentSeminars> studentSeminars = studentSeminarRepository.findByStudentId(String.valueOf(currentUserId));

        List<Seminar> activeSeminars = new ArrayList<>();
        Date currentDate = new Date();

        for (StudentSeminars stuseminar : studentSeminars) {
            Long seminarId = Long.valueOf(stuseminar.getSeminarId());
            Optional<Seminar> seminarDataOpt = seminarRepository.findById(seminarId);

            if (seminarDataOpt.isPresent()) {
                Seminar seminar = seminarDataOpt.get();
                Date endDate = seminar.getSeminarEndDate();

                // Əgər bitmə tarixi indiki zamandan sonradırsa (yəni hələ bitməyibsə)
                if (endDate != null && endDate.after(currentDate)) {
                    activeSeminars.add(seminar);
                }
            }
        }

        return activeSeminars;
    }

}
