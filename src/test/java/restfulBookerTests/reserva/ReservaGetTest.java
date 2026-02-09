package restfulBookerTests.reserva;

import baseTests.BaseTest;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class ReservaGetTest extends BaseTest {

    @Test
    public void reserva_MostraTodaReserva_RetornarComStatus200() {

        RestAssured.given()
                .spec(requestSpec)
            .when()
               .get(BOOKING)
            .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/reserva/RestfulBookerReservaSchema.json"))
        ;
    }

    @Test
    public void reserva_ComId_RetornarComStatus200() {

        RestAssured.given()
                .spec(requestSpec)
            .when()
               .get(BOOKING+"/2")
            .then()
                .log().body()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/reserva/RestfulBookerReservaIdSchema.json"))
        ;
    }

    @Test
    public void reserva_ComIdInexistente_RetornarComStatus404() {

        RestAssured.given()
                .spec(requestSpec)
            .when()
               .get(BOOKING+"/999999")
            .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Not Found"))
        ;
    }

    @Test
    public void reserva_IdLetras_RetornarComStatus404() {

        RestAssured.given()
                .spec(requestSpec)
            .when()
               .get(BOOKING+"/booking")
            .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Not Found"))
        ;
    }

    @Test
    public void reserva_IdCaracterEspecial_RetornarComStatus404() {

        RestAssured.given()
                .spec(requestSpec)
            .when()
               .get(BOOKING+"/!@#$")
            .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Not Found"))
        ;
    }

}
