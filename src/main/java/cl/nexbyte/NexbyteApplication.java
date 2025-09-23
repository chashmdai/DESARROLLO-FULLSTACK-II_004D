package cl.nexbyte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "cl.nexbyte.repository")
@EntityScan(basePackages = "cl.nexbyte.model")
public class NexbyteApplication {
  public static void main(String[] args) {
    SpringApplication.run(NexbyteApplication.class, args);
  }
}
