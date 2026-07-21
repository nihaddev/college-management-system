package az.edu.bbkk.backend.security;

import az.edu.bbkk.backend.entity.Student;
import az.edu.bbkk.backend.repositories.StudentRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;
import javax.crypto.SecretKey;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final StudentRepository studentRepository;

    @Value("${jwt.secret:SizinChoxUzunVeTukensizSecretKeyiniz123456789}")
    private String jwtSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // 1. Cookie-dən token-i əldə edirik
            String token = extractTokenFromCookie(request, "bbkk-auth");

            if (token != null) {
                // 2. Gizli açarla JWT-ni dekod edirik
                SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

                Claims claims = Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

                // 3. Tip çıxarışı (Inference) xətasının qarşısını alan təhlükəsiz ID konvertasiyası
                Long userId = extractUserIdFromClaims(claims);

                // 4. Əgər ID tapılarsa və istifadəçi hələ doğrulunmayıbsa bazadan axtarırıq
                if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    // Tip qarışıqlığının qarşısını almaq üçün explicit olaraq Student tipində saxlayırıq
                    Optional<Student> studentOptional = studentRepository.findById(userId);

                    if (studentOptional.isPresent()) {
                        Student student = studentOptional.get();

                        // Principal kimi birbaşa Student obyektini veririk
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                student,
                                null,
                                Collections.emptyList()
                        );

                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        // Context-ə əlavə edirik
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        logger.warn("Token etibarlıdır, lakin ID-si " + userId + " olan tələbə bazada tapılmadı.");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("JWT Doğrulama xətası: ", e);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Claims obyektindən ID-ni tip xətası olmadan çıxarmaq üçün köməkçi metod.
     */
    private Long extractUserIdFromClaims(Claims claims) {
        Object rawId = claims.get("id");

        if (rawId != null) {
            return Long.valueOf(String.valueOf(rawId));
        }

        if (claims.getSubject() != null) {
            return Long.valueOf(claims.getSubject());
        }

        return null;
    }

    /**
     * Request-dən xüsusi adlı cookie-ni çıxarır.
     */
    private String extractTokenFromCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}