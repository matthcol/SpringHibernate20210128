package movieapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieappApplication {

	public static void main(String[] args) {
		// Spring prend en charge l'application
		// en recevant en paramètre la classe principale
		// et scanne le package de l'application (et ses sous-packages)
		SpringApplication.run(MovieappApplication.class, args);
	}

}
