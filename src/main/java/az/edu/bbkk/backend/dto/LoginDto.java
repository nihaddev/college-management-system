package az.edu.bbkk.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginDto {
    private String username;
    private String email;
    private String password;
    public LoginDto() {}

    // get/set username
    @NotBlank(message = "username sahesi bos ola bilmez")
    @Size(min=2,max=14, message="2-14 arasi olmalidir username")
    public String getUsername(){return username;}
    public String setUsername(String username) {
        this.username = username;
        return username;
    }

    // get/set email
    @NotBlank(message = "email sahesi bos ola bilmez")
    @Email(
            regexp = "^[a-zA-Z0-9._%+-]+@bbkk\\.edu\\.az$",
            message = "Email ünvanı mütləq @bbkk.edu.az ilə bitməlidir"
    )
    public String getEmail(){ return email;}
    public String setEmail(String email) {
        this.email = email;
        return email;
    }

    // get/set password
    @NotBlank(message = "password sahesi bos olmamalidir")
    @Size(min=8, max=16, message="parol minimum 8 ve maximum 16 uzunluqunda olmalidir")

    public String getPassword(){return password;}
    public String setPassword(String password){
        this.password = password;
        return password;
    }

}
