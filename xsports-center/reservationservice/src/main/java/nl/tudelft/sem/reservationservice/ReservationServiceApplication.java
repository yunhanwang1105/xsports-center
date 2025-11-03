package nl.tudelft.sem.reservationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ReservationServiceApplication {

    /**
     * Main method for this application.
     *
     * @param args The arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ReservationServiceApplication.class, args);
    }

    /**
     * Method to get rest template.
     *
     * @return t The rest template
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
