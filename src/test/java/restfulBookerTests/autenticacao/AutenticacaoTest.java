package restfulBookerTests.autenticacao;

import baseTests.BaseTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import modelos.autenticacao.AuthRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import report.ExtentReportManager;
import report.Setup;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(Setup.class)
public class AutenticacaoTest extends BaseTest {

    @Test
    public void autenticar_CriarToken_RetornarTokenComStatus200() throws JsonProcessingException {

        ExtentReportManager.logInfoDetails("Iniciando teste: Criar token com credenciais válidas");
        ExtentReportManager.logInfoDetails("Request:");

        AuthRequest auth = new AuthRequest();
        auth.setUsername("admin");
        auth.setPassword("password123");

        String requestJson = new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(auth);

        ExtentReportManager.logJson(requestJson);

        Response response =
        RestAssured.given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(auth)
            .when()
                .post(AUTH);

        response
            .then()
                .log().body()
                .statusCode(200)
                .body("token", notNullValue())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/autenticacao/RestfulBookerAutenticacaoSchema.json"))
        ;

        ExtentReportManager.logInfoDetails("Response:");
        ExtentReportManager.logJson(response.asPrettyString());
        ExtentReportManager.logInfoDetails("Status Code: " + response.getStatusCode());
        ExtentReportManager.logPassDetails("Teste executado com sucesso");
    }

    @Test
    public void autenticar_SenhaInvalida_RetornarComStatus400() throws JsonProcessingException {

        ExtentReportManager.logInfoDetails("Iniciando teste: Senha inválida informada");
        ExtentReportManager.logInfoDetails("Request:");

        AuthRequest auth = new AuthRequest();
        auth.setUsername("admin");
        auth.setPassword("0123456789");

        String requestJson = new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(auth);

        ExtentReportManager.logJson(requestJson);

        Response response =
        RestAssured.given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(auth)
            .when()
                .post(AUTH);
        response
            .then()
                .log().body()
                .statusCode(200)
                .body("reason", equalTo("Bad credentials"))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/autenticacao/RestfulBookerAutenticacaoSchemaInvalido.json"));

        ExtentReportManager.logInfoDetails("Response:");
        ExtentReportManager.logJson(response.asPrettyString());
        ExtentReportManager.logInfoDetails("Status Code: " + response.getStatusCode());
        ExtentReportManager.logPassDetails("Teste executado com sucesso");
    }

    @Test
    public void autenticar_SemBody_RetornarComStatus400() {

        ExtentReportManager.logInfoDetails("Iniciando teste: Executando teste sem envio de username e password no body");
        ExtentReportManager.logInfoDetails("Request:");

        ExtentReportManager.logJson("{ }");

        Response response =
        RestAssured.given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
            .when()
                .post(AUTH);
        response
            .then()
                .log().body()
                .statusCode(200)
                .body("reason", equalTo("Bad credentials"))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/autenticacao/RestfulBookerAutenticacaoSchemaInvalido.json"));

        ExtentReportManager.logInfoDetails("Response:");
        ExtentReportManager.logJson(response.asPrettyString());
        ExtentReportManager.logInfoDetails("Status Code: " + response.getStatusCode());
        ExtentReportManager.logPassDetails("Teste executado com sucesso");
    }

    @Test
    public void autenticar_EndPointInvalido_RetornarComStatus404() {

        ExtentReportManager.logInfoDetails("Iniciando teste: Executando teste com endpoint inválido");
        ExtentReportManager.logInfoDetails("Endpoint inválido: " + AUTH + "qwert");

        Response response =
        RestAssured.given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
            .when()
                .post(AUTH+"qwert");
        response
            .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Not Found"));

        ExtentReportManager.logInfoDetails("Status Code: " + response.getStatusCode());
        ExtentReportManager.logPassDetails("Teste executado com sucesso");
    }

}
