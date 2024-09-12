package rbo13.nem12.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    @Bean
    CommandLineRunner run() {
        return args -> {
            if (args.length > 0) {
                // TODO:: process given csv file
            } else {
                System.out.println("CSV file not found. Please specify!");
            }
        };
    }
}
