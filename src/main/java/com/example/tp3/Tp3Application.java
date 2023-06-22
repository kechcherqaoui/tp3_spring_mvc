package com.example.tp3;

import com.example.tp3.entities.Patient;
import com.example.tp3.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;

@SpringBootApplication
public class Tp3Application
	implements CommandLineRunner
{
	@Autowired
	private PatientRepository patientRepository;

	public static void main(String[] args) {
		SpringApplication.run(Tp3Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		patientRepository.save(new Patient(null, "Ibrahim", new Date(), false, 950));
		patientRepository.save(new Patient(null, "Zakaria", new Date(), false, 120));
		patientRepository.save(new Patient(null, "Rachid", new Date(), false, 650));
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return  new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager){
		PasswordEncoder passwordEncoder=passwordEncoder();
		return args -> {
			jdbcUserDetailsManager.createUser(
				User.withUsername("admin")
					.password(passwordEncoder.encode("1234"))
					.roles("USER", "ADMIN")
					.build()
			);
			jdbcUserDetailsManager.createUser(
				User.withUsername("user")
					.password(passwordEncoder.encode("1234"))
					.roles("USER")
					.build()
			);
		};
	}
}
