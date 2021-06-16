package za.co.ieh.mpa.miroservices.mealservice;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import za.co.ieh.mpa.miroservices.mealservice.repository.IngredientRepository;
import za.co.ieh.mpa.miroservices.mealservice.repository.MealRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class MealServiceApplication implements CommandLineRunner {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MealRepository mealRepository;

	@Autowired
	IngredientRepository ingredientService;

	public static void main(String[] args) {
		SpringApplication.run(MealServiceApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {

	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}

}
