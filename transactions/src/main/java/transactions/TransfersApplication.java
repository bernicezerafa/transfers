package transactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ComponentScan("transactions")
@EnableJpaAuditing
public class TransfersApplication {

    public static void main(final String[] args) {
        SpringApplication.run(TransfersApplication.class, args);
    }
}
