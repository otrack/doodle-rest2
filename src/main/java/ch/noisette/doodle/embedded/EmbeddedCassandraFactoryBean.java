package ch.noisette.doodle.embedded;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import info.archinnov.achilles.embedded.CassandraEmbeddedServerBuilder;
import info.archinnov.achilles.persistence.PersistenceManager;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by bperroud on 12-Nov-14.
 */
@Configuration
@EnableAutoConfiguration
public class EmbeddedCassandraFactoryBean {

    private static PersistenceManager manager;

    @Bean
    protected PersistenceManager persistenceManager() throws Exception {

        if (manager == null) {
            manager = CassandraEmbeddedServerBuilder
                    .withEntityPackages("ch.noisette.doodle.entity")
                    .withKeyspaceName("doodle")
                    .cleanDataFilesAtStartup(true)
                    .withCQLPort(9041)
                    .buildPersistenceManager();
        }
        return manager;
    }
}
