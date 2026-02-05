package restfulBookerTests.autenticacao;

import baseTests.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;
import restfulBookerTests.modelos.autenticacao.AuthRequest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AutenticacaoTest extends BaseTest {

    @Test
    public void autenticar_CriarToken_RetornarTokenComStatus200() {

        AuthRequest auth = new AuthRequest();
        auth.setUsername("admin");
        auth.setPassword("password123");

        RestAssured.given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(auth)
            .when()
                .post(AUTH)
            .then()
                .log().body()
                .statusCode(200)
                .body("token", notNullValue())
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/autenticacao/RestfulBookerAutenticacaoSchema.json"))
                ;
    }

    @Test
    public void autenticar_SenhaInvalida_RetornarComStatus400() {
        AuthRequest auth = new AuthRequest();
        auth.setUsername("admin");
        auth.setPassword("0123456789");

        RestAssured.given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(auth)
            .when()
                .post(AUTH)
            .then()
                .log().body()
                .statusCode(200)
                .body("reason", equalTo("Bad credentials"))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/autenticacao/RestfulBookerAutenticacaoSchemaInvalido.json"))
        ;
    }

    @Test
    public void autenticar_SemBody_RetornarComStatus400() {

        RestAssured.given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
            .when()
                .post(AUTH)
            .then()
                .log().body()
                .statusCode(200)
                .body("reason", equalTo("Bad credentials"))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/autenticacao/RestfulBookerAutenticacaoSchemaInvalido.json"))
        ;
    }

    @Test
    public void autenticar_EndPointInvalido_RetornarComStatus404() {

        RestAssured.given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
            .when()
                .post(AUTH+"qwert")
            .then()
                .log().body()
                .statusCode(404)
                .body(equalTo("Not Found"))
        ;
    }

}
