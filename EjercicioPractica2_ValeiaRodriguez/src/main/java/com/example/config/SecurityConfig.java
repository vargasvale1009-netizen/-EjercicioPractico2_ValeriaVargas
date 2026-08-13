package com.example.config;

import com.example.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomSuccessHandler customSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                // Recursos públicos
                .antMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
                // ADMIN: acceso total
                .antMatchers("/usuarios/**", "/roles/**").hasRole("ADMIN")
                // ADMIN y MEDICO: gestión de citas
                .antMatchers("/citas/nueva", "/citas/*/editar",
                             "/citas/*/eliminar", "/citas/*/estado")
                             .hasAnyRole("ADMIN", "MEDICO")
                // ADMIN, MEDICO y PACIENTE: ver citas y consultas
                .antMatchers("/citas", "/citas/*").hasAnyRole("ADMIN", "MEDICO", "PACIENTE")
                .antMatchers("/consultas/**").hasAnyRole("ADMIN", "MEDICO", "PACIENTE")
                // Cualquier otra ruta requiere autenticación
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(customSuccessHandler)
                .failureUrl("/login?error=true")
                .permitAll()
            .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
            .and()
            .exceptionHandling()
                .accessDeniedPage("/access-denied");
    }
}
