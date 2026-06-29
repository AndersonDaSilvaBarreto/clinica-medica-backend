package com.topicos_especiais_1.clinica_medica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableAsync
public class ClinicaMedicaApplication {

	public static void main(String[] args) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		IO.println("senha: " + passwordEncoder.encode("Senha123%"));
		SpringApplication.run(ClinicaMedicaApplication.class, args);
	}
}
