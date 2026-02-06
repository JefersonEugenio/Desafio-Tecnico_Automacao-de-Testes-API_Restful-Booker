package restfulBookerTests.reserva;

import baseTests.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;
import restfulBookerTests.fabricas.RestfulBookerReservaFabrica;

public class ReservaTest extends BaseTest {

    @Test
    public void reserva_MostraTodaReserva_RetornarComStatus200() {

        RestAssured.given()
                .spec(requestSpec)
            .when()
               .get(BOOKING)
            .then()
                .log().body()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/reserva/RestfulBookerReservaSchema.json"))
        ;
    }

    @Test
    public void reserva_ComId_RetornarComStatus200() {

        RestAssured.given()
                .spec(requestSpec)
            .when()
               .get(BOOKING+"/1")
            .then()
                .log().body()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/reserva/RestfulBookerReservaIdSchema.json"))
        ;
    }

    @Test
    public void reserva_CriarReserva_RetornarComStatus200() {

        RestAssured.given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(RestfulBookerReservaFabrica.criarReserva())
            .when()
               .post(BOOKING)
            .then()
                .log().body()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/reserva/RestfulBookerReservarCriarSchema.json"))
        ;
    }

}
