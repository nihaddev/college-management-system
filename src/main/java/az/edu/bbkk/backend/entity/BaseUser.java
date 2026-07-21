package az.edu.bbkk.backend.entity;

public abstract class BaseUser {
    private Long id;
    private String finCode;
    private String email;
    private String password;
    private String role;


    public BaseUser(Long id, String finCode, String email, String password, String role) {
        this.id = id;
        this.finCode = finCode;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getUserSummary() {
        return "ID: " + id + " | Fincode:  " + finCode + " | Email: " + email + " | Role: " + role;
    }

    public abstract boolean hasPermission(String action);
    public abstract String getDashboardRoute();
    public abstract  String getSeminarStats();

    public Long getId() { return id; }
    public String getFinCode() { return finCode; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}