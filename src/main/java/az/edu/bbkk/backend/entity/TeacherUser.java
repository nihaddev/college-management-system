package az.edu.bbkk.backend.entity;

public class TeacherUser extends BaseUser {
    private String department; // Kafedra

    public TeacherUser(Long id, String finCode, String email, String password, String department) {
        super(id,finCode, email, password, "TEACHER");
        this.department = department;
    }


    @Override
    public boolean hasPermission(String action) {
        return action.equals("VIEW_GRADES") || action.equals("EDIT_GRADES");
    }


    @Override
    public String getDashboardRoute() {
        return "/api/v1/teacher/dashboard";
    }

    public String getDepartment() { return department; }
    public String getSeminarStats(){ return  "";}
}