package olive.oliveyoung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OliveyoungApplication {

    public static void main(String[] args) {
        SpringApplication.run(OliveyoungApplication.class, args);
    }

}



