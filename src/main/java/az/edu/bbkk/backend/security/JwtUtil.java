package az.edu.bbkk.backend.security;

import az.edu.bbkk.backend.entity.Student;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret:SizinChoxUzunVeTukensizSecretKeyiniz123456789}")
    private String jwtSecret;

    public String generateToken(Student student) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .subject(student.getUsername())
                .claim("id", student.getId())
                .claim("finCode", student.getFinCode())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // 1 gün
                .signWith(key)
                .compact();
    }
}