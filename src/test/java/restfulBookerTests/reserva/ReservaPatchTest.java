package restfulBookerTests.reserva;

import baseTests.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import modelos.reserva.ReservaRequest;
import modelos.reserva.ReservaResponse;
import org.junit.jupiter.api.Test;
import utils.Token;

import static org.hamcrest.Matchers.equalTo;

public class ReservaPatchTest extends BaseTest {

    @Test
    public void reserva_AtualizadoParcialReserva_RetornarComStatus200() {

        ReservaResponse reservaAtual =
                RestAssured.given()
                        .spec(requestSpec)
                        .when()
                        .get(BOOKING + "/3")
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(ReservaResponse.class);

        String token = Token.autenticar_CriarEObterToken();

        ReservaRequest reservaRequest = new ReservaRequest();
        reservaRequest.setFirstname("Jeferson");
        reservaRequest.setLastname("Eugenio");

        reservaRequest.setTotalprice(reservaAtual.getTotalprice());
        reservaRequest.setDepositpaid(reservaAtual.isDepositpaid());
        reservaRequest.setBookingdates(reservaAtual.getBookingdates());
        reservaRequest.setAdditionalneeds(reservaAtual.getAdditionalneeds());

        RestAssured.given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(reservaRequest)
                .when()
                .patch(BOOKING+"/3")
                .then()
                .log().body()
                .statusCode(200)
                .body("firstname", equalTo("Jeferson"))
                .body("lastname", equalTo("Eugenio"))
        ;
    }

}
