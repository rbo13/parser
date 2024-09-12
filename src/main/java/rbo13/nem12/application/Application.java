package rbo13.nem12.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import rbo13.nem12.application.service.MeterReadingService;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    @Bean
    CommandLineRunner run(MeterReadingService service) {
        return args -> {
            if (args.length > 0) {
                // if we want to save in a database
                // service.process(args[0]);
                service.printInsertStatements(args[0]);
            } else {
                System.out.println("CSV file not found. Please specify!");
            }
        };
    }
}
