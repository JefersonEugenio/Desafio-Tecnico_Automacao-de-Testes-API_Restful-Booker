package report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.junit.jupiter.api.extension.*;

import java.util.Arrays;

public class Setup implements TestWatcher, BeforeAllCallback, AfterAllCallback, BeforeEachCallback {

    private static ExtentReports extentReports;
    public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void beforeAll(ExtensionContext context){
        System.out.println("Inicio da execução (JUnit 5)");
        String fileName = ExtentReportManager.getReportNameWithTimeStamp();
        String fullReportPath = System.getProperty("user.dir") + "\\reports\\" + fileName;
        extentReports = ExtentReportManager.createInstance(fullReportPath, "Test API Automation Report", "Restful Booker API");
    }

    @Override
    public void afterAll(ExtensionContext context) {
        if (extentReports != null) {
            extentReports.flush();
        }
        System.out.println("Fim da execução (JUnit 5)");
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        String testClass = context.getRequiredTestClass().getName();
        String testName = context.getRequiredTestMethod().getName();
        ExtentTest test = extentReports.createTest("Teste nome " + testClass + " - " + testName);
        extentTest.set(test);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        ExtentReportManager.logFailureDetails(cause.getMessage());

        String stackTrace = Arrays.toString(cause.getStackTrace());
        stackTrace = stackTrace.replaceAll(",", "<br>");
        String formatedTrace = "<details>\n" +
                "<summary>Click Here To See Exception Logs</summary>\n" +
                " "+stackTrace+"\n" +
                "</details>\n";
        ExtentReportManager.logExceptionDetails(formatedTrace);
        System.out.println("Falhou: " + context.getDisplayName());
    }
}
