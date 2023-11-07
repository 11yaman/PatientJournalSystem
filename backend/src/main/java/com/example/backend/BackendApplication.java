package com.example.backend;

import com.example.backend.model.Employee;
import com.example.backend.model.Patient;
import com.example.backend.service.AuthService;
import com.example.backend.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	private final AuthService authService;

	@Autowired
	public BackendApplication(AuthService authService) {
		this.authService = authService;
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@PostConstruct
	public void postConstruct() {
		//authService.register(new Patient("yaman", "1234", "Yaman", "A"));
		//authService.register(new Employee("noor", "1234", "Noor", "H", Employee.Position.OTHER));
		//authService.register(new Employee("Doctor", "d", "doctor", "1234", Employee.Position.DOCTOR));
	}

}
