package baseTests;

import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import specs.RequestSpecFactory;
import utils.EndPoints;

public class BaseTest extends EndPoints {

    protected RequestSpecification requestSpec;

    @BeforeEach
    public void setUp() {

        requestSpec = RequestSpecFactory.get();

    }

}
