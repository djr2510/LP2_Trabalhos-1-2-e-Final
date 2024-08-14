package LP2.Trabalho_final;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TrabalhoFinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrabalhoFinalApplication.class, args);
	}

}
