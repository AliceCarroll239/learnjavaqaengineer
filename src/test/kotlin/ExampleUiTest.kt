import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.*
import org.openqa.selenium.WebDriver
import utils.TestUtils
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.openqa.selenium.By
import utils.Numbers

@TestInstance(Lifecycle.PER_CLASS)
class ExampleUiTest {

    private val driver: WebDriver = TestUtils().configureFirefoxWebDriver(TestUtils().getCurrentWorkingDirectory())

    @Test
    fun checkDriver() {
        driver.get("https://playground.learnqa.ru/puzzle/triangle/")
        val minimizeButton = driver.findElement(By.cssSelector("[onclick]"))
        minimizeButton.click()

        val inputA = driver.findElement(By.cssSelector(".js_a"))
        val inputB = driver.findElement(By.cssSelector(".js_b"))
        val inputC = driver.findElement(By.cssSelector(".js_c"))
        val submit = driver.findElement(By.cssSelector(".btn-submit"))
        var result = ""

        Numbers().fillForm(inputA, "100", inputB, "100", inputC, "100", submit)
        result = driver.findElement(By.cssSelector(".info")).text
        assertThat(result, `is`("Это равносторонний треугольник.\nВы ввели:\nA: 100; B: 100; C: 100"))

        Numbers().fillForm(inputA, "10", inputB, "10", inputC, "5", submit)
        result = driver.findElement(By.cssSelector(".info")).text
        assertThat(result, `is`("Это равнобедренный треугольник.\nВы ввели:\nA: 10; B: 10; C: 5"))


    }

//    @AfterAll
//    fun driverClose() {
//        driver.close()
//    }
}