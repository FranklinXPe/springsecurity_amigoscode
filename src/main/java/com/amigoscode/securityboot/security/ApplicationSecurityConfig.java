package com.amigoscode.securityboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity // con esta anotacion decimos que aqui se hara toda la configuracion de WebSecurity en nuestra aplicacion
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
        // Con estas dos sentencias siguientes: especificamos los paterns que permitira el acceso sin solicitar el usuario y la contraseña
                .antMatchers("/","index","/css/*","/js/*")
                .permitAll()

                .anyRequest()
                .authenticated()
                .and()
                .httpBasic(); // Utilizamos la autenticacion Básica (mostrara ventana emergente de login en el navegador )

    }

    //Cuando sobreescribes este método, desaparece la configuración por defecto de SpringSecurity para la autenticación básica ("httpBasic")
    // y ya no va generar el mensaje en consola como lo hacia, así: "Using generated security password: aba1e97b-0f6c-497d-8ffb-55f5a2912498"
    // sino que ahora usará el que lo definamos en UserDetails.
    // También se debe tener en cuenta que se a creado como atributo un @Autowired PasswordConfig para realizar la codificación del password
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails annaSmithUser= User.builder()
                .username("annasmith")
                 // .password("password") //-> ya que se requiere encriptar el password ahora debemos usar el:PasswordEncoder
                .password(passwordEncoder.encode("pass"))
                .roles("STUDENT") // en la base de datos, la columna roles aparecera con un prefijo: ROLE_STUDENT
                .build();

        return new InMemoryUserDetailsManager(annaSmithUser);
    }


}
