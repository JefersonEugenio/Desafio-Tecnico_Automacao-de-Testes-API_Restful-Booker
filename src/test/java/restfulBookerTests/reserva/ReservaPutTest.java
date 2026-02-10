package restfulBookerTests.reserva;

import baseTests.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import modelos.reserva.ReservaDataRequest;
import modelos.reserva.ReservaRequest;
import org.junit.jupiter.api.Test;
import utils.Token;

import java.time.LocalDate;

public class ReservaPutTest extends BaseTest {

    @Test
    public void reserva_AtualizadoReserva_RetornarComStatus200() {

        String token = Token.autenticar_CriarEObterToken();

        LocalDate date = LocalDate.now();
        ReservaDataRequest reservaDataRequest = new ReservaDataRequest();
        reservaDataRequest.setCheckin(date.toString());
        reservaDataRequest.setCheckout(date.plusDays(15).toString());

        ReservaRequest reservaRequest = new ReservaRequest();
        reservaRequest.setFirstname("Jeferson");
        reservaRequest.setLastname("Eugenio");
        reservaRequest.setTotalprice(1000);
        reservaRequest.setDepositpaid(true);
        reservaRequest.setBookingdates(reservaDataRequest);
        reservaRequest.setAdditionalneeds("Quadra FUTSAL");

        RestAssured.given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(reservaRequest)
            .when()
                .put(BOOKING+"/3")
            .then()
                .log().body()
                .statusCode(200)
        ;
    }

}
