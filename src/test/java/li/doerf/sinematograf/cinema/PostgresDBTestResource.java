package li.doerf.sinematograf.cinema;

import java.util.HashMap;
import java.util.Map;

import org.testcontainers.containers.PostgreSQLContainer;

import io.quarkus.logging.Log;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class PostgresDBTestResource implements QuarkusTestResourceLifecycleManager {
    
    private PostgreSQLContainer<?> postgres;

    @Override
    public Map<String, String> start() {
        postgres = new PostgreSQLContainer<>("postgres:17-alpine");
        postgres.start();
        Log.info("started postgres container");
        Map<String, String> conf = new HashMap<>();
        conf.put("quarkus.datasource.jdbc.url", postgres.getJdbcUrl());
        conf.put("quarkus.datasource.username", postgres.getUsername());
        conf.put("quarkus.datasource.password", postgres.getPassword());
        conf.put("quarkus.datasource.reactive.url", postgres.getJdbcUrl().replace("jdbc:",  ""));
        return conf;
    }

    @Override
    public void stop() {
        postgres.stop();
        Log.info("stopped postgres container");
    }

}
