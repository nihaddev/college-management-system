package az.edu.bbkk.backend.controller;


import az.edu.bbkk.backend.entity.Seminar;
import az.edu.bbkk.backend.entity.Student;
import az.edu.bbkk.backend.entity.StudentSeminars;
import az.edu.bbkk.backend.entity.groups;
import az.edu.bbkk.backend.service.StudentService;
import az.edu.bbkk.backend.service.SeminarService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/groups")
public class GroupsController {
    private final StudentService studentService;
    private final SeminarService seminarService;

    public GroupsController(StudentService studentService, SeminarService seminarService) {
        this.studentService = studentService;
        this.seminarService = seminarService;
    }

    @GetMapping
    public ResponseEntity<?> getStudentClasses(@AuthenticationPrincipal Student student, HttpServletResponse response) {
        if (student == null) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "İstifadəçi tapılmadı və ya token keçərsizdir."));
        }
        Object stgroup = studentService.getStudentGroup(String.valueOf(student.getId()));

        return ResponseEntity.ok(Map.of("message", stgroup));

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGroupWithId(
            @AuthenticationPrincipal Student student,
            @PathVariable String id,
            HttpServletResponse response) {


        groups getgroup = studentService.getStudentGroupById(id);

        Student starostaDetails = studentService.getStudentDetails(getgroup.getStarostaId());

        Map<String, Object> modifiedresponse = new HashMap<>();
        modifiedresponse.put("id", getgroup.getId());
        modifiedresponse.put("groupId", getgroup.getGroupId());
        modifiedresponse.put("name", getgroup.getName());
        modifiedresponse.put("faculty", getgroup.getFaculty());

        Map<String, String> starostaMap = new HashMap<>();
        starostaMap.put("name", starostaDetails.getName());
        starostaMap.put("surname", starostaDetails.getSurname());

        modifiedresponse.put("starosta", starostaMap);
        modifiedresponse.put("studentsCount", getgroup.getStudentsCounts());
        modifiedresponse.put("endDate", getgroup.getEndDate());

        return ResponseEntity.ok(Map.of("data", modifiedresponse));
    }

    @GetMapping("/{id}/seminars")
    public ResponseEntity<?> getGroupSeminars(
            @AuthenticationPrincipal Student student,
            @PathVariable String id,
            @RequestParam(defaultValue = "active") String status,
            HttpServletResponse response) {


        /* StudentSeminars getgroup = studentService.getStudentSeminarsWithGroupId(String.valueOf(student.getId()), id);
         */
        //StudentSeminars getgroup = studentService.getStudentSeminarsWithGroupId(id);
        groups getgroup = studentService.getStudentGroupById(id);
        if ("active".equalsIgnoreCase(status)) {
           Seminar activeSeminars = (Seminar) seminarService.getStudentAllActiveSemminars();
           return ResponseEntity.ok(Map.of("data", activeSeminars));
        }

        Map<String, Object> modifiedresponse = new HashMap<>();
        modifiedresponse.put("id", getgroup.getId());
        modifiedresponse.put("groupId", getgroup.getGroupId());
        modifiedresponse.put("name", getgroup.getName());
        modifiedresponse.put("faculty", getgroup.getFaculty());




        return ResponseEntity.ok(Map.of("data", modifiedresponse));
    }
}
