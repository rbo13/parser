package rbo13.nem12.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import rbo13.nem12.application.service.MeterReadingService;

@SpringBootApplication
@EnableJdbcRepositories
@EnableTransactionManagement
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    @Bean
    CommandLineRunner run(MeterReadingService service) {
        return args -> {
            if (args.length > 0) {
                service.process(args[0]);
            } else {
                System.out.println("CSV file not found. Please specify!");
            }
        };
    }
}
