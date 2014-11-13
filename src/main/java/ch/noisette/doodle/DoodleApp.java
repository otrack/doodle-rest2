package ch.noisette.doodle;

import info.archinnov.achilles.persistence.PersistenceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by bperroud on 11-Nov-14.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class DoodleApp {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(DoodleApp.class, args);
    }

}
