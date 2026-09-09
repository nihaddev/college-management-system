package az.edu.bbkk.backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "teachers")
@Getter                 // Getter-ləri avtomatik generasiya edir
@Setter                 // Setter-ləri avtomatik generasiya edir
@NoArgsConstructor      // Boş konstruktoru mütləq yaradır (JPA üçün lazımdır)
@AllArgsConstructor
public class Teacher {
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
    private String faculty;
    private String email;
    private String password;
    private String joinDate;
    private String leaveDate;
}
