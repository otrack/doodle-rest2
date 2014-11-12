package ch.noisette.doodle.app;

import com.datastax.driver.core.Cluster;
import info.archinnov.achilles.persistence.PersistenceManager;
import info.archinnov.achilles.persistence.PersistenceManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;

/**
 * Created by bperroud on 11-Nov-14.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("ch.noisette.doodle")
public class DoodleApp {

    @Autowired
    PersistenceManager persistenceManager;

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(DoodleApp.class, args);
    }
}
