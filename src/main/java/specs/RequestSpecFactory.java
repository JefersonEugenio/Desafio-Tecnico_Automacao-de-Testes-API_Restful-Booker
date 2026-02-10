package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import utils.EndPoints;

public class RequestSpecFactory extends EndPoints {

    public static RequestSpecification get() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI_BOOKER)
                .build();
    }
}
