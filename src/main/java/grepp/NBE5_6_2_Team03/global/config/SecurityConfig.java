package grepp.NBE5_6_2_Team03.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(
                        (auth) -> auth
                                .requestMatchers("/", "/users/**").permitAll()
                                .requestMatchers("/css/**", "/assets/**", "/js/**").permitAll()
                                .anyRequest().authenticated()
                );

        http.csrf((auth) -> auth.disable());

        http
                .formLogin(auth -> auth
                        .loginProcessingUrl("/users/login-process")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/users")
                        .permitAll()
                );

        return http.build();
    }
}
