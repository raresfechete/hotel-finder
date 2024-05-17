package com.example.demo;

import com.example.demo.util.LoadFromFile;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class HotelApplication {

	private final LoadFromFile loadFromFile;

	@Autowired
	public HotelApplication(LoadFromFile loadFromFile) {
		this.loadFromFile = loadFromFile;
	}

	public static void main(String[] args) {
		SpringApplication.run(HotelApplication.class, args);
	}

	//some magic to avoid using static beans
	@PostConstruct
	public void loadData() {
		try {
			loadFromFile.populateFromJsonFile();
		} catch (IOException e) {
			System.err.println("Error loading hotels from JSON: " + e.getMessage());
		}
	}
}
