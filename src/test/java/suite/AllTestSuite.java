package suite;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("restfulBookerTests.reserva")
@IncludeTags("get")
public class AllTestSuite {

}
