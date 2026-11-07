package az.edu.bbkk.backend.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Semestr")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Semestr {
    private Long id; // semestrId

    private String semestrTitle;
    private String semestrDescription;

    private String semestrStartDate;
    private String semestrEndDate;

    private String maxPointStudentId; // maksimum bal toplayan diger telebeler icinde bu semestr uzre olan telebenin idsi
}
