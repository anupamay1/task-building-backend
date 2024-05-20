package com.task.building;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin("*")
public class TaskBuildingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskBuildingApplication.class, args);
	}

}
