package az.edu.bbkk.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name="students")
@Getter                 // Getter-ləri avtomatik generasiya edir
@Setter                 // Setter-ləri avtomatik generasiya edir
@NoArgsConstructor      // Boş konstruktoru mütləq yaradır (JPA üçün lazımdır)
@AllArgsConstructor     // Bütün sahələri olan konstruktoru yaradır
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String name;
    private String surname;

    @Column(unique = true, nullable = false)
    private String finCode;
    private String dateOfBirth;
    private String groupId;
    private String faculty;
    private String password;
}