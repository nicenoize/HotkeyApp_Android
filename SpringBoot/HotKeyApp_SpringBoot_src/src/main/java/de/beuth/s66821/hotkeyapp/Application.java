package de.beuth.s66821.hotkeyapp;
/**
 * @author Alexander Stahl
 * @version 2017-10-30
 */

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@EnableSpringConfigured //for spring-aspects
public class Application {

	// Logger for console-output
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CommandLineRunner startCommandLineRunner() {
		return args -> {
			log.info("HotKeyApplication is ready ...");
		};
	}
}