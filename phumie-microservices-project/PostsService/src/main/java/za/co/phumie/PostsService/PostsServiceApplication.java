package za.co.phumie.PostsService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
@Import(za.phumie.shared.config.RedissonConfig.class)
@EntityScan(basePackages = {
		"za.co.phumie.PostsService",
		"za.phumie.shared.appmodels"
})
//@EnableJpaRepositories(basePackages = {
//		"za.co.phumie.PostsService.repository"
//})
public class PostsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostsServiceApplication.class, args);
	}

}
