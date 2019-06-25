import com.google.gson.Gson
import data.TriangleDataClass
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.*
import org.openqa.selenium.WebDriver
import utils.TestUtils
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.openqa.selenium.By
import utils.DataProvider
import utils.Numbers

@TestInstance(Lifecycle.PER_CLASS)
class ExampleUiTest {

    private val driver: WebDriver = TestUtils().configureFirefoxWebDriver(
        TestUtils().getCurrentWorkingDirectory())
    private val paramsFile = DataProvider().loadFileAsString(
        DataProvider().getCurrentWorkingDirectory()
            .resolve("src/test/resources/params.json"))
    private val testSettings = Gson().fromJson(paramsFile, TriangleDataClass::class.java)

    @Test
    fun checkDriver() {
        //Костыль
        //JavaScript error: https://playground.learnqa.ru/js/puzzle/jquery-3.2.1.min.js,
        // line 5: TypeError: document.querySelector(...) is null
        driver.manage().window().maximize()
        for (i in 1..2) {
            driver.get("https://playground.learnqa.ru/puzzle/triangle/")
            Thread.sleep(2000)
        }

        val minimizeButton = driver.findElement(By.cssSelector("[onclick]"))
        minimizeButton.click()

        val inputA = driver.findElement(By.cssSelector(".js_a"))
        val inputB = driver.findElement(By.cssSelector(".js_b"))
        val inputC = driver.findElement(By.cssSelector(".js_c"))
        val submit = driver.findElement(By.cssSelector(".btn-submit"))
        var result = ""

        testSettings.data.cases.forEach {
            Numbers().fillForm(inputA, it.a, inputB, it.b, inputC, it.c, submit)
            result = driver.findElement(By.cssSelector(".info")).text
            assertThat(result, `is`(it.expectedResult))
        }

        testSettings.data.bugs.forEach {
            Numbers().fillForm(inputA, it.a, inputB, it.b, inputC, it.c, submit)
            result = driver.findElement(By.cssSelector(".info")).text
            assertThat(result, `is`(it.expectedResult))
        }
    }

    @AfterAll
    fun driverClose() {
        driver.close()
    }
}