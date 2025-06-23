package thelazycoder.school_expenditure_management.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import thelazycoder.school_expenditure_management.Repository.UserRepository;
import thelazycoder.school_expenditure_management.ServiceImpl.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserRepository userRepository;
    private final JWTFilterChain jwtFilterChain;
    public SecurityConfig(UserRepository userRepository, JWTFilterChain jwtFilterChain) {
        this.userRepository = userRepository;
        this.jwtFilterChain = jwtFilterChain;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .formLogin(form-> form.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(e->e
                        .requestMatchers("/","/swagger-ui/**",
                                "/v3/api-docs*/**",
                                "/actuator/**",
                                "/api/department/all").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/department/create").hasRole("ADMIN")
                        .requestMatchers("/api/vendor/create").hasRole("ADMIN")
                        .requestMatchers("/api/user/updateRole/**").hasRole("ADMIN")
                        .requestMatchers("/api/expenditure/finance_approval").hasRole("FINANCE_OFFICER")
                        .anyRequest().authenticated())
                .authenticationProvider(authenticationProvider())
                .sessionManagement(e->e.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .addFilterBefore(jwtFilterChain, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
        public UserDetailsService userDetailsService(UserRepository userRepository) {
            return new CustomUserDetailsService(userRepository);

        }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager(){
//        UserDetails user = User.withUsername("user")
//                .password(passwordEncoder().encode("password"))
//                .roles("USER").build();
//        return new InMemoryUserDetailsManager(user);
//    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService(userRepository));
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
