package com.amigoscode.securityboot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
/**
 * El password debe ser codificado, caso contrario si el password lo pasamos en texto plano
 * nos mostrará un error de tipo IllegalArgumentException: There is no PasswordEncoder mapped for the id "null".
 *
 * Al establecer la anotación @Configuration el bean de tipo UserDetailsService implementado en ApplicationSecurityConfig
 * buscara que el password pasado como parámetro sea de tipo BCrypt, no en texto plano
 * */
@Configuration
public class PasswordConfig {
    // El Password Enconder tiene 3 metodos importantes: encode, matches y upgradeEnconding
    // y Spring utiliza el metodo "enconde" para codificar el password
    @Bean
    public PasswordEncoder passwordEncoder(){

        // BCryptPasswordEncoder es el más famoso codificador de password
       return new BCryptPasswordEncoder(10);
    }
}
