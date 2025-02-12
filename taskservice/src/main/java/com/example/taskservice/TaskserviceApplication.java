package com.example.taskservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TaskserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskserviceApplication.class, args);
	}

}
