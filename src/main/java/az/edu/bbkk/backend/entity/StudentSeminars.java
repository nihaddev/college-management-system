package az.edu.bbkk.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name="student_seminars")
@Getter                 // Getter-ləri avtomatik generasiya edir
@Setter                 // Setter-ləri avtomatik generasiya edir
@NoArgsConstructor      // Boş konstruktoru mütləq yaradır (JPA üçün lazımdır)
@AllArgsConstructor     // Bütün sahələri olan konstruktoru yaradır
public class StudentSeminars {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String username;

    @Column(unique = true, nullable = false)
    private String finCode;

    private String groupId;
    private String faculty;
    private String seminarId;
    private String seminarTitle;
    private String seminarStartDate;
    private String seminarEndDate;
    private String seminarPointOfStudent;


}