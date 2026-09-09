package az.edu.bbkk.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {

    // Validation Qrupları
    public interface UsernameGroup {}
    public interface EmailGroup {}
    public interface FinCodeGroup {}

    @NotBlank(message = "İstifadəçi adı boş ola bilməz.", groups = UsernameGroup.class)
    private String username;

    @NotBlank(message = "Email boş ola bilməz.", groups = EmailGroup.class)
    private String email;

    @NotBlank(message = "FIN kod boş ola bilməz.", groups = FinCodeGroup.class)
    private String finCode;

    // Şifrə bütün giriş tipləri üçün məmburidir:
    @NotBlank(message = "Şifrə boş ola bilməz.", groups = {UsernameGroup.class, EmailGroup.class, FinCodeGroup.class})
    private String password;
}