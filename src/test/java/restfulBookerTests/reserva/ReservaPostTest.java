package restfulBookerTests.reserva;

import baseTests.BaseTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fabricas.RestfulBookerReservaFabrica;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import modelos.reserva.ReservaRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import report.ExtentReportManager;
import report.Setup;

import static org.hamcrest.Matchers.equalTo;

@ExtendWith(Setup.class)
public class ReservaPostTest extends BaseTest {

    @Test
    public void reserva_CriarReserva_RetornarComStatus200() throws JsonProcessingException {

        ExtentReportManager.logInfoDetails("Executando teste: Criar reserva com token");
        ExtentReportManager.logInfoDetails("Request:");

        String requestJson = new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(RestfulBookerReservaFabrica.criarReserva());

        ExtentReportManager.logJson(requestJson);

        Response response =
        RestAssured.given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(requestJson)
            .when()
                .post(BOOKING);
        response
            .then()
                .log().body()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/reserva/RestfulBookerReservarCriarSchema.json"))
        ;

        ExtentReportManager.logInfoDetails("Status Code: " + response.getStatusCode());
        ExtentReportManager.logInfoDetails("Response:");
        ExtentReportManager.logJson(response.asPrettyString());
        ExtentReportManager.logPassDetails("Teste executado com sucesso");
    }

    @Test
    public void reserva_CriarReservaVazioBody_RetornarComStatus400() {

        ExtentReportManager.logInfoDetails("Iniciando teste: Executando teste sem envio no body");
        ExtentReportManager.logInfoDetails("Request:");

        ExtentReportManager.logJson("{ }");

        Response response =
        RestAssured.given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
            .when()
                .post(BOOKING);
        response
            .then()
                .log().body()
                .statusCode(500)
                .body(equalTo("Internal Server Error"));

        ExtentReportManager.logInfoDetails("Response: " + response.asString());
        ExtentReportManager.logInfoDetails("Status Code: " + response.getStatusCode());
        ExtentReportManager.logPassDetails("Teste executado com sucesso");
    }

    @Test
    public void reserva_CriarReservaFaltaFirstName_RetornarComStatus500() throws JsonProcessingException {

        ExtentReportManager.logInfoDetails("Executando teste: Falta preenche FirstName");
        ExtentReportManager.logInfoDetails("Request:");

        ReservaRequest reservaRequest = RestfulBookerReservaFabrica.criarReserva();
        reservaRequest.setFirstname(null);

        String requestJson = new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(reservaRequest);

        ExtentReportManager.logJson(requestJson);

        Response response =
        RestAssured.given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(requestJson)
            .when()
                .post(BOOKING);
        response
            .then()
                .log().body()
                .statusCode(500)
                .body(equalTo("Internal Server Error"));

        ExtentReportManager.logInfoDetails("Status Code: " + response.getStatusCode());
        ExtentReportManager.logInfoDetails("Response: " + response.asString());
        ExtentReportManager.logPassDetails("Teste executado com sucesso");
    }

    @Test
    public void reserva_CriarReservaFaltaLastName_RetornarComStatus500() throws JsonProcessingException {

        ExtentReportManager.logInfoDetails("Executando teste: Falta preenche LastName");
        ExtentReportManager.logInfoDetails("Request:");

        ReservaRequest reservaRequest = RestfulBookerReservaFabrica.criarReserva();
        reservaRequest.setLastname(null);

        String requestJson = new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(reservaRequest);

        ExtentReportManager.logJson(requestJson);

        Response response =
        RestAssured.given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(requestJson)
            .when()
                .post(BOOKING);
        response
            .then()
                .log().body()
                .statusCode(500)
                .body(equalTo("Internal Server Error"));

        ExtentReportManager.logInfoDetails("Status Code: " + response.getStatusCode());
        ExtentReportManager.logInfoDetails("Response: " + response.asString());
        ExtentReportManager.logPassDetails("Teste executado com sucesso");
    }

    @Test
    public void reserva_CriarReservaTotalPriceNegativo_RetornarComStatus400() throws JsonProcessingException {

        ExtentReportManager.logInfoDetails("Executando teste: Negativo valor 'Totalprice'");
        ExtentReportManager.logInfoDetails("Request:");

        ReservaRequest reservaRequest = RestfulBookerReservaFabrica.criarReserva();
        reservaRequest.setTotalprice(-100);

        String requestJson = new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(reservaRequest);

        ExtentReportManager.logJson(requestJson);

        Response response =
        RestAssured.given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(requestJson)
            .when()
                .post(BOOKING);
        response
            .then()
                .log().body()
                .statusCode(200);

        ExtentReportManager.logInfoDetails("Status Code: " + response.getStatusCode());
        ExtentReportManager.logInfoDetails("Response:");
        ExtentReportManager.logJson(response.asPrettyString());
        ExtentReportManager.logPassDetails("Teste executado com sucesso");
    }

}
