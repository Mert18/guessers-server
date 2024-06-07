package dev.m2t.unlucky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UnluckyApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnluckyApplication.class, args);
	}

}
