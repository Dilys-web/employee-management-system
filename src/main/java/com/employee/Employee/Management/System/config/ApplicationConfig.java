package com.employee.Employee.Management.System.config;

import com.employee.Employee.Management.System.dto.request.RegisterDto;
import com.employee.Employee.Management.System.repository.UserRepository;
import com.employee.Employee.Management.System.service.interfaces.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

import static com.employee.Employee.Management.System.enums.Role.ADMIN;
import static com.employee.Employee.Management.System.enums.Role.MANAGER;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository repository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(
                Arrays.asList()
        );
        config.setAllowedHeaders(Arrays.asList(
                HttpHeaders.ORIGIN,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT,
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS
        ));
        config.setAllowedMethods(Arrays.asList(
                "GET",
                "POST",
                "DELETE",
                "PUT",
                "PATCH"
        ));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);

    }
//
    @Bean
public CommandLineRunner commandLineRunner(
        AuthenticationService service
) {
    return args -> {
        // Check if the admin user already exists
        if (!repository.findByEmail("admin@mail.com").isPresent()) {
            var admin = RegisterDto.builder()
                    .firstName("Admin")
                    .lastName("Admin")
                    .employeeEmail("admin@mail.com")
                    .employeePassword("password")
                    .employeePasswordConfirm("password")
                    .role(ADMIN)
                    .build();
            System.out.println("Admin token: " + service.register(admin).getAccessToken());
        } else {
            System.out.println("Admin user already exists.");
        }

        // Check if the manager user already exists
        if (!repository.findByEmail("manager@mail.com").isPresent()) {
            var manager = RegisterDto.builder()
                    .firstName("Manager")
                    .lastName("Manager")
                    .employeeEmail("manager@mail.com")
                    .employeePassword("password")
                    .employeePasswordConfirm("password") // Ensure this is included if required
                    .role(MANAGER)
                    .build();
            System.out.println("Manager token: " + service.register(manager).getAccessToken());
        } else {
            System.out.println("Manager user already exists.");
        }
    };
}

}