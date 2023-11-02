package com.example.backend;

import com.example.backend.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	private final UserService userService;

	@Autowired
	public BackendApplication(UserService userService) {
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@PostConstruct
	public void postConstruct() {
		//userService.createUser(new Patient("Yaman", "A", "yaman", "112233"));
		//userService.createUser(new Employee("Noor", "H", "noor", "112233", Employee.Position.OTHER));
		//userService.createUser(new Employee("Doctor", "d", "doctor", "112233", Employee.Position.DOCTOR));
	}

}
