package az.edu.bbkk.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterDto {
    private String username ;
    private String groupId;
    private String fullname;
    private String password;

    public RegisterDto(){}

    //get/set username
    @NotBlank(message="username bos ola bilmez")
    @Size(min=5,max=14,message="5-14 arasi olmalidir")
    public String getUsername(){return username;}
    public String setUsername(String username){
        this.username = username;
        return username;
    }
    //get/set groupId
    @NotBlank(message="groupId bos ola bilmez")
    @Size(min=1,max=25,message="1-25 arasi olmalidir")
    public String getGroupId(){
        return groupId;
    }
    public String setGroupId(String groupId){
        this.groupId = groupId;
        return groupId;
    }
    // get/set fullname
    @NotBlank(message = "bos ola bilmez")
    @Size(min = 3,max = 20, message="3-20 arasi olmalidir")
    public String getFullname(){
        return fullname;
    }
    public String setFullname(String fullname){
        this.fullname = fullname;
        return fullname;
    }
    //get/set password
    @NotBlank(message = "password sahesi bos olmamalidir")
    @Size(min=8, max=16, message="parol minimum 8 ve maximum 16 uzunluqunda olmalidir")
    public String getPassword(){return password;}
    public String setPassword(String password){
        this.password = password;
        return password;
    }
}
