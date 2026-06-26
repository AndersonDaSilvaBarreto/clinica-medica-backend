package com.topicos_especiais_1.clinica_medica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableCaching
@EnableAsync
public class ClinicaMedicaApplication {
	static void main(String[] args) {
		SpringApplication.run(ClinicaMedicaApplication.class, args);
	}
}
