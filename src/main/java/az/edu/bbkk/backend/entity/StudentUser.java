package az.edu.bbkk.backend.entity;

public class StudentUser extends BaseUser {
    private String studentNumber; // Tələbə biletinin nömrəsi

    public StudentUser(Long id, String finCode, String email, String password, String studentNumber) {
        super(id, finCode, email, password, "STUDENT"); // BaseUser constructor-unu çağırır
        this.studentNumber = studentNumber;
    }

    // Tələbə yalnız qiymətlərə baxa bilər, qiymət yaza bilməz
    @Override
    public boolean hasPermission(String action) {
        return action.equals("VIEW_GRADES");
    }

    @Override
    public String getSeminarStats(){

        return "";
    }


    @Override
    public String getDashboardRoute() {
        return "/api/v1/student/dashboard";
    }

    public String getStudentNumber() { return studentNumber; }
}