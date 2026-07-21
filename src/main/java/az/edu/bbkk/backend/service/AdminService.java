package az.edu.bbkk.backend.service;

import az.edu.bbkk.backend.entity.Admins;
import az.edu.bbkk.backend.repositories.AdminRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.Map;

@Service
@Validated
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
    public Boolean isAdmin(String id){
        return adminRepository.existsById(Long.valueOf(id));
    }
    public String getPermLevel(String id){
       if (!isAdmin(id))  new RuntimeException("Admin deyil!");
        Admins admin = adminRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Admin tapılmadı!"));

        return admin.getPermLevel();
    }


    public boolean checkPermLevel(String id, String requiredPermission) {
        if (!isAdmin(id)) {
            throw new RuntimeException("İcazə yoxdur: İstifadəçi admin deyil!");
        }


        Admins admin = adminRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Admin tapılmadı!"));

        String userPerm = admin.getPermLevel(); // 'superadmin', 'admin', 'dean'


        Map<String, Integer> hierarchy = Map.of(
                "faculty", 1,
                "dean", 2,
                "admin", 3,
                "superadmin", 4
        );

        int userLevel = hierarchy.getOrDefault(userPerm.toLowerCase(), 0);
        int requiredLevel = hierarchy.getOrDefault(requiredPermission.toLowerCase(), 0);

        // 4. Əgər istifadəçinin dərəcəsi tələb olunan dərəcəyə bərabər və ya ondan böyükdürsə TRUE qaytarır
        return userLevel >= requiredLevel;
    }

}
