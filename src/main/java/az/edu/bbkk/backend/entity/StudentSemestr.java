package az.edu.bbkk.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "StudentSemestr")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentSemestr {
    private Long semestrId;
    private String semestrTitle; // like "2025-2026 Qis Semestr imtahani

    private String studentId;
    private String name;
    private String surname;
    private String username;

    @Column(unique = true, nullable = false)
    private String finCode;

    private String groupId;
    private String faculty;

    private Number semestrGpa;
    private Boolean isAvaliableForStudent;
    private String semestrStartDate;
    private String semestrEndDate;
}
