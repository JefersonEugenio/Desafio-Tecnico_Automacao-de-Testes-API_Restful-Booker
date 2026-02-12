package restfulBookerTests.ping;

import baseTests.BaseTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class PingGetTest extends BaseTest {

    @Test
    public void ping_HealthCheck_RetornarComStatus200() {

        RestAssured.given()
                .spec(requestSpec)
            .when()
                .get(PING)
            .then()
                .log().body()
                .statusCode(201)
                .body(equalTo("Created"))
        ;
    }

}
