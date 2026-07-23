package az.edu.bbkk.backend.controller;


import az.edu.bbkk.backend.entity.Student;
import az.edu.bbkk.backend.entity.groups;
import az.edu.bbkk.backend.service.StudentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/groups")
public class GroupsController {
    private final StudentService studentService;

    public GroupsController(StudentService studentService) {
        this.studentService = studentService;
    }
   @GetMapping
   public ResponseEntity<?> getStudentClasses(@AuthenticationPrincipal Student student,HttpServletResponse response){
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


        groups getgroup = studentService.getStudentGroupById(String.valueOf(student.getId()), id);

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
}
