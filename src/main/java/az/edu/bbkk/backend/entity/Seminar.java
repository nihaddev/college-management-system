package az.edu.bbkk.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Entity
@Table(name="seminars")
@Getter                 // Getter-ləri avtomatik generasiya edir
@Setter                 // Setter-ləri avtomatik generasiya edir
@NoArgsConstructor      // Boş konstruktoru mütləq yaradır (JPA üçün lazımdır)
@AllArgsConstructor     // Bütün sahələri olan konstruktoru yaradır
public class Seminar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seminarId;
    private String groupId;
    private String faculty;
    private String seminarTitle;
    private String seminarStartDate;
    private String seminarEndDate;
    private String teacherId;
    private String teacherName;
    private String totalPoints;


}