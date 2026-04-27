package za.co.likesService.LikeAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {
		"za.co.likesService.LikeAPI",
		"za.phumie.shared.appmodels"
})
public class LikeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LikeApiApplication.class, args);
	}

}
