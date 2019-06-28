import com.google.gson.Gson
import data.Bug
import data.Case
import data.TriangleDataClass
import io.qameta.allure.Description
import io.qameta.allure.Feature
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import page.Page
import utils.DataProvider
import utils.Numbers
import utils.TestUtils
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
        val page = Page(driver)
        page.minimizeButtonClick()
    }

    @DisplayName("Тест кейсы")
    @Description("Заполнение форм и результаты")
    @Feature("Треугольник")
    @ParameterizedTest
    @MethodSource("utils.Provider#getCases")
    fun checkCases(testCase: Case) {
        val page = Page(driver)
        var result = ""

        Numbers().fillForm(page.inputA, testCase.a, page.inputB, testCase.b, page.inputC, testCase.c, page.submit)
        result = driver.findElement(By.cssSelector(".info")).text
        assertThat(result, `is`(testCase.expectedResult))
    }

    @DisplayName("Баги")
    @Description("Заполнение форм и результаты")
    @Feature("Треугольник")
    @ParameterizedTest
    @MethodSource("utils.Provider#getBugs")
    fun checkBugs(testCase: Bug) {
        val page = Page(driver)
        var result = ""

        Numbers().fillForm(page.inputA, testCase.a, page.inputB, testCase.b, page.inputC, testCase.c, page.submit)
        result = driver.findElement(By.cssSelector(".info")).text
        assertThat(result, `is`(testCase.expectedResult))
    }

    @AfterAll
    fun driverClose() {
        driver.close()
    }
}