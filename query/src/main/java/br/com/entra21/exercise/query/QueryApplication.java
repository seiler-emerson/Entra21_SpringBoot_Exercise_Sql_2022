package br.com.entra21.exercise.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class QueryApplication implements CommandLineRunner {

	@Autowired
	private JdbcTemplate jdbc;
	
	public static void main(String[] args) {
		SpringApplication.run(QueryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		
	}
}
