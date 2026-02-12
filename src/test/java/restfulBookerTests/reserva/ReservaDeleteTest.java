package restfulBookerTests.reserva;

import baseTests.BaseTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import utils.Token;

import static org.hamcrest.Matchers.equalTo;

public class ReservaDeleteTest extends BaseTest {

    @Test
    public void reserva_DeleteId_RetornarComStatus201() {

        String token = Token.autenticar_CriarEObterToken();

        RestAssured.given()
                .spec(requestSpec)
                .cookie("token", token)
            .when()
                .delete(BOOKING+"/2")
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
