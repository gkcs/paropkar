package paropkar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.logging.Logger;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Application {
    private static final Logger logger = Logger.getLogger(Application.class.getSimpleName());

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        logger.info("System started successfully");
    }
}