package restfulBookerTests.reserva;

import baseTests.BaseTest;
import fabricas.RestfulBookerReservaFabrica;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import report.ExtentReportManager;
import report.Setup;
import utils.Token;

import static org.hamcrest.Matchers.equalTo;

@ExtendWith(Setup.class)
public class ReservaDeleteTest extends BaseTest {

    @Test
    public void reserva_DeleteId_RetornarComStatus201() {

        ExtentReportManager.logInfoDetails("Executando teste: Delete reserva com ID existente");
        ExtentReportManager.logInfoDetails("Request:");

        Response response =
        RestAssured.given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(RestfulBookerReservaFabrica.criarReserva())
            .when()
                .post(BOOKING);

        response
                .then()
                .statusCode(200);

        int bookingId = response.jsonPath().getInt("bookingid");

        ExtentReportManager.logJson(response.asPrettyString());
        ExtentReportManager.logInfoDetails("Reserva criada com ID: " + bookingId);

        String token = Token.autenticar_CriarEObterToken();

        ExtentReportManager.logInfoDetails("Token: " + token);

        Response responseDelete =
        RestAssured.given()
                .spec(requestSpec)
                .cookie("token", token)
            .when()
                .delete(BOOKING+"/"+bookingId);

        responseDelete
            .then()
                .statusCode(201)
                .body(equalTo("Created"));

        ExtentReportManager.logInfoDetails("Status Code: " +  responseDelete.getStatusCode());
        ExtentReportManager.logInfoDetails("Response: " + responseDelete.asString());
        ExtentReportManager.logPassDetails("Teste executado com sucesso");
    }

    @Test
    public void reserva_DeleteIdInExistente_RetornarComStatus404() {

        ExtentReportManager.logInfoDetails("Executando teste: Delete reserva com ID inexistente");
        ExtentReportManager.logInfoDetails("Endpoint: /999999");

        String token = Token.autenticar_CriarEObterToken();

        ExtentReportManager.logInfoDetails("Token: " + token);

        Response response =
        RestAssured.given()
                .spec(requestSpec)
                .cookie("token", token)
            .when()
                .delete(BOOKING+"/999999");
        response
            .then()
                .log().body()
                .statusCode(405)
                .body(equalTo("Method Not Allowed"))
        ;

        ExtentReportManager.logInfoDetails("Status Code: " +  response.getStatusCode());
        ExtentReportManager.logInfoDetails("Response: " + response.asString());
        ExtentReportManager.logPassDetails("Teste executado com sucesso");
    }

    @Test
    public void reserva_DeleteIdSemToken_RetornarComStatus403() {

        ExtentReportManager.logInfoDetails("Executando teste: Delete reserva com ID e sem token");
        ExtentReportManager.logInfoDetails("Reserva com ID /1 e sem token");

        Response response =
        RestAssured.given()
                .spec(requestSpec)
            .when()
                .delete(BOOKING+"/1");
        response
            .then()
                .log().body()
                .statusCode(403)
                .body(equalTo("Forbidden"))
        ;

        ExtentReportManager.logInfoDetails("Status Code: " +  response.getStatusCode());
        ExtentReportManager.logInfoDetails("Response: " + response.asString());
        ExtentReportManager.logPassDetails("Teste executado com sucesso");
    }

}
