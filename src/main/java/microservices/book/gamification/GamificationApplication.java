package microservices.book.gamification;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GamificationApplication {

	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext context = SpringApplication.run(GamificationApplication.class, args);
		System.out.println("Hit enter to terminate");
		System.in.read();
		context.close();
	}
}
