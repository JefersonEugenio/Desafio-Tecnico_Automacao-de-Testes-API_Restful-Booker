package restfulBookerTests.reserva;

import baseTests.BaseTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fabricas.RestfulBookerReservaFabrica;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import modelos.reserva.ReservaDataRequest;
import modelos.reserva.ReservaRequest;
import modelos.reserva.ReservaResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import report.ExtentReportManager;
import report.Setup;
import utils.Token;

import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;

@ExtendWith(Setup.class)
public class ReservaPutTest extends BaseTest {

    @Test
    public void reserva_AtualizadoReserva_RetornarComStatus200() throws JsonProcessingException {

        ExtentReportManager.logInfoDetails("Executando teste: Atualizar reserva");
        ExtentReportManager.logInfoDetails("Request:");

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

        String requestJson = new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(reservaRequest);

        ExtentReportManager.logJson(requestJson);

        Response response =
                RestAssured.given()
                        .spec(requestSpec)
                        .contentType(ContentType.JSON)
                        .cookie("token", token)
                        .body(requestJson)
                        .when()
                        .put(BOOKING + "/3");
        response
                .then()
                .log().body()
                .statusCode(200)
                .body("firstname", equalTo("Jeferson"))
                .body("lastname", equalTo("Eugenio"))
                .body("totalprice", equalTo(1000))
                .body("depositpaid", equalTo(true))
                .body("bookingdates.checkin", equalTo(date.toString()))
                .body("bookingdates.checkout", equalTo(date.plusDays(15).toString()))
                .body("additionalneeds", equalTo("Quadra FUTSAL"));

        ExtentReportManager.logInfoDetails("Status Code: " + response.getStatusCode());
        ExtentReportManager.logInfoDetails("Response:");
        ExtentReportManager.logJson(response.asPrettyString());
        ExtentReportManager.logPassDetails("Teste executado com sucesso");

    }

    @Test
    public void reserva_PutIdInexistente_RetornarComStatus404() throws JsonProcessingException {

        ExtentReportManager.logInfoDetails("Executando teste: Atualizar reserva com ID inexistente");
        ExtentReportManager.logInfoDetails("Endpoint: " + BOOKING + "/9999");
        ExtentReportManager.logInfoDetails("Request:");

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

        String requestJson = new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(reservaRequest);

        ExtentReportManager.logJson(requestJson);

        Response response =
        RestAssured.given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(requestJson)
            .when()
                .put(BOOKING+"/9999");
        response
            .then()
                .log().body()
                .statusCode(405);

        ExtentReportManager.logInfoDetails("Status Code: " + response.getStatusCode());
        ExtentReportManager.logInfoDetails("Response: " + response.asString());
        ExtentReportManager.logPassDetails("Teste executado com sucesso");
    }
}
