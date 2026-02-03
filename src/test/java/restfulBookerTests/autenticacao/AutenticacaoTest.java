package restfulBookerTests.autenticacao;

import baseTests.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import restfulBookerTests.modelos.autenticacao.AuthRequest;

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
                ;
    }
}
