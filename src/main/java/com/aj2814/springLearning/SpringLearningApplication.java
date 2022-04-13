package com.aj2814.springLearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(GithubProperties.class)
public class SpringLearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringLearningApplication.class, args);
	}
}
