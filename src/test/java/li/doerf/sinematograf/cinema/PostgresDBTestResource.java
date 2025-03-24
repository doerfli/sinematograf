package li.doerf.sinematograf.cinema;

import java.util.HashMap;
import java.util.Map;

import org.testcontainers.containers.PostgreSQLContainer;

import io.quarkus.logging.Log;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class PostgresDBTestResource implements QuarkusTestResourceLifecycleManager {
    
    private PostgreSQLContainer<?> postgres;
    private PostgreSQLContainer<?> postgres2;

    @Override
    public Map<String, String> start() {
        postgres = new PostgreSQLContainer<>("postgres:17-alpine");
        postgres.start();
        postgres2 = new PostgreSQLContainer<>("postgres:17-alpine");
        postgres2.start();
        Log.info("started postgres container");
        Map<String, String> conf = new HashMap<>();
        conf.put("quarkus.datasource.jdbc.url", postgres.getJdbcUrl());
        conf.put("quarkus.datasource.username", postgres.getUsername());
        conf.put("quarkus.datasource.password", postgres.getPassword());
        conf.put("quarkus.datasource.cinema.jdbc.url", postgres2.getJdbcUrl() + "&currentSchema=cinema&search_path=cinema");
        conf.put("quarkus.datasource.cinema.username", postgres2.getUsername());
        conf.put("quarkus.datasource.cinema.password", postgres2.getPassword());
        conf.put("quarkus.datasource.cinema.schema", "cinema");
        conf.put("quarkus.flyway.cinema.migrate-at-start", "true");
        return conf;
    }

    @Override
    public void stop() {
        postgres.stop();
        Log.info("stopped postgres container");
    }

}
