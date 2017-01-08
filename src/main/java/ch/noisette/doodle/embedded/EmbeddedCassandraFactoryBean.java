package ch.noisette.doodle.embedded;

import info.archinnov.achilles.embedded.CassandraEmbeddedServerBuilder;
import info.archinnov.achilles.persistence.PersistenceManager;

/**
 * Created by bperroud on 12-Nov-14.
 */
//@Configuration
//@EnableAutoConfiguration
public class EmbeddedCassandraFactoryBean {

    private static PersistenceManager manager;

//    @Bean
    protected PersistenceManager persistenceManager() throws Exception {

        if (manager == null) {
            manager = CassandraEmbeddedServerBuilder
                    .withEntityPackages("ch.noisette.doodle.entity")
                    .withKeyspaceName("doodle")
                    .cleanDataFilesAtStartup(true)
                    .withStoragePort(30000)
                    .withCQLPort(30001)
                    .withThriftPort(30002)
                    .withThriftPort(30003)
                    .buildPersistenceManager();
        }
        return manager;
    }
}
