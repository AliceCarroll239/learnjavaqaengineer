import com.google.gson.Gson
import data.Bug
import data.Case
import data.TriangleDataClass
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.jupiter.api.*
import org.openqa.selenium.WebDriver
import utils.TestUtils
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.openqa.selenium.By
import utils.DataProvider
import utils.Numbers
import java.util.concurrent.TimeUnit

@TestInstance(Lifecycle.PER_CLASS)
class ExampleUiTest {

    private val driver: WebDriver = TestUtils().configureFirefoxWebDriver(
        TestUtils().getCurrentWorkingDirectory())
    private val paramsFile = DataProvider().loadFileAsString(
        DataProvider().getCurrentWorkingDirectory()
            .resolve("src/test/resources/params.json"))
    private val testSettings = Gson().fromJson(paramsFile, TriangleDataClass::class.java)

    @BeforeAll
    fun openPage() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
        driver.manage().window().maximize()
        //Костыль, js ошибкана странице
        //TODO вынести в page factory
        for (i in 1..2) {
            driver.get("https://playground.learnqa.ru/puzzle/triangle/")
            Thread.sleep(2000)
        }
        val minimizeButton = driver.findElement(By.cssSelector("[onclick]"))
        minimizeButton.click()
    }

    @ParameterizedTest
    @MethodSource("utils.Provider#getCases")
    fun checkCases(testCase: Case) {
        //TODO вынести в page factory
        val inputA = driver.findElement(By.cssSelector(".js_a"))!!
        val inputB = driver.findElement(By.cssSelector(".js_b"))!!
        val inputC = driver.findElement(By.cssSelector(".js_c"))!!
        val submit = driver.findElement(By.cssSelector(".btn-submit"))!!
        var result = ""

        Numbers().fillForm(inputA, testCase.a, inputB, testCase.b, inputC, testCase.c, submit)
        result = driver.findElement(By.cssSelector(".info")).text
        assertThat(result, `is`(testCase.expectedResult))
    }

    @ParameterizedTest
    @MethodSource("utils.Provider#getBugs")
    fun checkBugs(testCase: Bug) {
        //TODO вынести в page factory
        val inputA = driver.findElement(By.cssSelector(".js_a"))!!
        val inputB = driver.findElement(By.cssSelector(".js_b"))!!
        val inputC = driver.findElement(By.cssSelector(".js_c"))!!
        val submit = driver.findElement(By.cssSelector(".btn-submit"))!!
        var result = ""

        Numbers().fillForm(inputA, testCase.a, inputB, testCase.b, inputC, testCase.c, submit)
        result = driver.findElement(By.cssSelector(".info")).text
        assertThat(result, `is`(testCase.expectedResult))
    }

    @AfterAll
    fun driverClose() {
        driver.close()
    }
}