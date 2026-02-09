package restfulBookerTests.reserva;

import baseTests.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;
import restfulBookerTests.fabricas.RestfulBookerReservaFabrica;
import restfulBookerTests.modelos.reserva.ReservaRequest;

import static org.hamcrest.Matchers.equalTo;

public class ReservaPostTest extends BaseTest {

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

    @Test
    public void reserva_CriarReservaVazioBody_RetornarComStatus500() {

        RestAssured.given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
            .when()
                .post(BOOKING)
            .then()
                .log().body()
                .statusCode(500)
                .body(equalTo("Internal Server Error"))
        ;
    }

    @Test
    public void reserva_CriarReservaFaltaFirstName_RetornarComStatus500() {

        ReservaRequest reservaRequest = RestfulBookerReservaFabrica.criarReserva();
        reservaRequest.setFirstname(null);

        RestAssured.given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(reservaRequest)
            .when()
                .post(BOOKING)
            .then()
                .log().body()
                .statusCode(500)
                .body(equalTo("Internal Server Error"))
        ;
    }

    @Test
    public void reserva_CriarReservaFaltaLastName_RetornarComStatus500() {

        ReservaRequest reservaRequest = RestfulBookerReservaFabrica.criarReserva();
        reservaRequest.setLastname(null);

        RestAssured.given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(reservaRequest)
            .when()
                .post(BOOKING)
            .then()
                .log().body()
                .statusCode(500)
                .body(equalTo("Internal Server Error"))
        ;
    }

    @Test
    public void reserva_CriarReservaTotalPriceNegativo_RetornarComStatus200() {

        ReservaRequest reservaRequest = RestfulBookerReservaFabrica.criarReserva();
        reservaRequest.setTotalprice(-100);

        RestAssured.given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(reservaRequest)
            .when()
                .post(BOOKING)
            .then()
                .log().body()
                .statusCode(200)
        ;
    }

}
