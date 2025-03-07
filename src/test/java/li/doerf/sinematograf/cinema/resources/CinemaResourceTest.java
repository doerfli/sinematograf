package li.doerf.sinematograf.cinema.resources;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.containsString;

import java.util.Random;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import li.doerf.sinematograf.cinema.PostgresDBTestResource;
import li.doerf.sinematograf.cinema.entity.CinemaEntity;

@QuarkusTest
@TestHTTPEndpoint(CinemaResource.class)
@QuarkusTestResource(value = PostgresDBTestResource.class, restrictToAnnotatedClass = true)
class CinemaResourceTest {


    @Test
    void testCreateEndpoint() {
        var cinemaName = "Cinema%f".formatted(new Random().nextDouble());
        // given
        given()
            .contentType("application/json")
            .body(
                """
                {
                    "name": "%s",
                    "street": "Street",
                    "zip": "12345",
                    "city": "City"
                }
                """.formatted(cinemaName)
            )
        .when()
            .post()
        // then
        .then()
            .statusCode(200);

        await().atMost(5, SECONDS).until(() -> {
            when()
                .get()
            .then()
                .statusCode(200)
                .body(containsString(cinemaName));
            return true;
        });

        assertThat(CinemaEntity.count()).isEqualTo(1);
    }

}
