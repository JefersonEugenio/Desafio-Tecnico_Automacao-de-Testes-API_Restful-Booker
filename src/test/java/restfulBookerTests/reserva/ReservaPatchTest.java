package restfulBookerTests.reserva;

import baseTests.BaseTest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import modelos.reserva.ReservaRequest;
import modelos.reserva.ReservaResponse;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import report.ExtentReportManager;
import report.Setup;
import utils.Token;

import static org.hamcrest.Matchers.equalTo;

@ExtendWith(Setup.class)
@Tag("patch")
public class ReservaPatchTest extends BaseTest {

    @Test
    public void reserva_AtualizadoParcialReserva_RetornarComStatus200() throws JsonProcessingException {

        ExtentReportManager.logInfoDetails("Executando teste: Atualizar parcial parte nome e sobrenome reserva ");

        ReservaResponse reservaAtual =
                RestAssured.given()
                        .spec(requestSpec)
                        .when()
                        .get(BOOKING + "/4")
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(ReservaResponse.class);

        String requestJson = new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(reservaAtual);

        ExtentReportManager.logInfoDetails("Antes atualização:");
        ExtentReportManager.logJson(requestJson);

        String token = Token.autenticar_CriarEObterToken();

        ReservaRequest reservaRequest = new ReservaRequest();

        reservaRequest.setFirstname("Jeferson");
        reservaRequest.setLastname("Eugenio");

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        String requestJsonAtualizado = mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(reservaRequest);

        ExtentReportManager.logInfoDetails("Atualização:");
        ExtentReportManager.logJson(requestJsonAtualizado);

        Response response =
                RestAssured.given()
                        .spec(requestSpec)
                        .contentType(ContentType.JSON)
                        .cookie("token", token)
                        .body(requestJsonAtualizado)
                        .when()
                        .patch(BOOKING + "/4");
        response
                .then()
                .log().body()
                .statusCode(200)
                .body("firstname", equalTo("Jeferson"))
                .body("lastname", equalTo("Eugenio"));

        ExtentReportManager.logInfoDetails("Status Code: " + response.getStatusCode());
        ExtentReportManager.logInfoDetails("Response:");
        ExtentReportManager.logJson(response.asPrettyString());
        ExtentReportManager.logPassDetails("Teste executado com sucesso");
    }

}
