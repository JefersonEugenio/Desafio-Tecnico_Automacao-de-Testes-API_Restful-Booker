package restfulBookerTests.reserva;

import baseTests.BaseTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import report.ExtentReportManager;
import report.Setup;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

@ExtendWith(Setup.class)
@Tag("get")
public class ReservaGetTest extends BaseTest {

    @Test
    public void reserva_MostraTodaReserva_RetornarComStatus200() throws JsonProcessingException {

        ExtentReportManager.logInfoDetails("Executando teste: Listar todas as reservas");
        ExtentReportManager.logInfoDetails("Endpoint: " + BOOKING);

        Response response =
        RestAssured.given()
                .spec(requestSpec)
            .when()
               .get(BOOKING);
       response
            .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/reserva/RestfulBookerReservaSchema.json"))
        ;

        List<Map<String, Object>> reservas =
                response.jsonPath().getList("$");

        List<Map<String, Object>> listaLimite =
                reservas.stream()
                        .limit(20)
                        .toList();

        String listas = new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(listaLimite);

        ExtentReportManager.logInfoDetails("Status Code: " + response.getStatusCode());
        ExtentReportManager.logInfoDetails("Response Body:");
        ExtentReportManager.logJson(listas);
        ExtentReportManager.logPassDetails("Teste executado com sucesso");
    }

    @Test
    @Tag("fulano")
    public void reserva_ComId_RetornarComStatus200() {

        ExtentReportManager.logInfoDetails("Executando teste: Busca reserva pelo ID existente");
        ExtentReportManager.logInfoDetails("Endpoint: " + BOOKING + "/2");

        Response response =
        RestAssured.given()
                .spec(requestSpec)
            .when()
               .get(BOOKING+"/2");
        response
            .then()
                .log().body()
                .statusCode(200)
//                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/reserva/RestfulBookerReservaIdSchema.json"))
        ;

        ExtentReportManager.logInfoDetails("Response Body:");
        ExtentReportManager.logJson(response.asPrettyString());
        ExtentReportManager.logInfoDetails("Status Code: " + response.getStatusCode());
        ExtentReportManager.logPassDetails("Teste executado com sucesso");
    }

    @Test
    public void reserva_ComIdInexistente_RetornarComStatus404() {

        ExtentReportManager.logInfoDetails("Executando teste: Busca reserva pelo ID inexistente");
        ExtentReportManager.logInfoDetails("Endpoint: " + BOOKING + "/999999");

        Response response =
        RestAssured.given()
                .spec(requestSpec)
            .when()
               .get(BOOKING+"/999999");
        response
            .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Not Found"));

        ExtentReportManager.logInfoDetails("Response Body: " + response.asString());
        ExtentReportManager.logInfoDetails("Status Code: " + response.getStatusCode());
        ExtentReportManager.logPassDetails("Teste executado com sucesso");
    }

    @Test
    public void reserva_IdLetras_RetornarComStatus404() {

        ExtentReportManager.logInfoDetails("Executando teste: Busca reserva pelo letras");
        ExtentReportManager.logInfoDetails("Endpoint: " + BOOKING + "/booking");

        Response response =
        RestAssured.given()
                .spec(requestSpec)
            .when()
               .get(BOOKING+"/booking");
        response
            .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Not Found"));

        ExtentReportManager.logInfoDetails("Response Body: " + response.asString());
        ExtentReportManager.logInfoDetails("Status Code: " + response.getStatusCode());
        ExtentReportManager.logPassDetails("Teste executado com sucesso");
    }

    @Test
    public void reserva_IdCaracterEspecial_RetornarComStatus404() {

        ExtentReportManager.logInfoDetails("Executando teste: Busca reserva pelo caracteres");
        ExtentReportManager.logInfoDetails("Endpoint: " + BOOKING + "/!@#$");

        Response response =
        RestAssured.given()
                .spec(requestSpec)
            .when()
               .get(BOOKING+"/!@#$");
        response
            .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Not Found"));

        ExtentReportManager.logInfoDetails("Response Body: " + response.asString());
        ExtentReportManager.logInfoDetails("Status Code: " + response.getStatusCode());
        ExtentReportManager.logPassDetails("Teste executado com sucesso");
    }

}
