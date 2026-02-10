package utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import modelos.autenticacao.AuthRequest;
import specs.RequestSpecFactory;

import static utils.EndPoints.AUTH;

public class Token {

    public static String autenticar_CriarEObterToken() {

        AuthRequest auth = new AuthRequest();
        auth.setUsername("admin");
        auth.setPassword("password123");

        return RestAssured.given()
                .spec(RequestSpecFactory.get())
                .contentType(ContentType.JSON)
                .body(auth)
            .when()
                .post(AUTH)
            .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

}
