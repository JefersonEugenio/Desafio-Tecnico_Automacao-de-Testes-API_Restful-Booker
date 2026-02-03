package baseTests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import utils.EndPoints;

public class BaseTest extends EndPoints {

    public static RequestSpecification requestSpec;

    @BeforeEach
    public void setUp() {

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URI_BOOKER)
                .build();

    }

}
