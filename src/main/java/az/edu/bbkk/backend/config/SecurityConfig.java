package az.edu.bbkk.backend.config;

import az.edu.bbkk.backend.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    // 1. Spring-in Filter-i inject etməsi üçün Konstruktor əlavə edirik
    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. CSRF-i söndürürük
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Session-u STATELESS edirik (JWT istifadə etdiyimiz üçün server-də session saxlamırıq)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 3. İcazələri tənzimləyirik
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                )

                // 4. MÜHÜM: Öz JWT Filter-imizi UsernamePasswordAuthenticationFilter-dən ƏVVƏL zəncirə qeyd edirik
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                // 5. İstəyə bağlı Basic Auth
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}