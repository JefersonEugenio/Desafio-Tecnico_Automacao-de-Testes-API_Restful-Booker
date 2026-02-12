package restfulBookerTests.reserva;

import baseTests.BaseTest;
import fabricas.RestfulBookerReservaFabrica;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import utils.Token;

import static org.hamcrest.Matchers.equalTo;

public class ReservaDeleteTest extends BaseTest {

    @Test
    public void reserva_DeleteId_RetornarComStatus201() {

        int bookingId = RestAssured.given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(RestfulBookerReservaFabrica.criarReserva())
            .when()
                .post(BOOKING)
            .then()
                .log().body()
                .statusCode(200)
                .extract()
                .path("bookingid");

        String token = Token.autenticar_CriarEObterToken();

        RestAssured.given()
                .spec(requestSpec)
                .cookie("token", token)
            .when()
                .delete(BOOKING+"/"+bookingId)
            .then()
                .log().body()
                .statusCode(201)
                .body(equalTo("Created"))
        ;
    }

    @Test
    public void reserva_DeleteIdInExistente_RetornarComStatus404() {

        String token = Token.autenticar_CriarEObterToken();

        RestAssured.given()
                .spec(requestSpec)
                .cookie("token", token)
            .when()
                .delete(BOOKING+"/999999")
            .then()
                .log().body()
                .statusCode(405)
                .body(equalTo("Method Not Allowed"))
        ;
    }

    @Test
    public void reserva_DeleteIdSemToken_RetornarComStatus403() {

        RestAssured.given()
                .spec(requestSpec)
            .when()
                .delete(BOOKING+"/1")
            .then()
                .log().body()
                .statusCode(403)
                .body(equalTo("Forbidden"))
        ;
    }

}
