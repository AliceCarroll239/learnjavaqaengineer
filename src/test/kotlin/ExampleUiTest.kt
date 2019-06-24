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
        //Костыль
        //JavaScript error: https://playground.learnqa.ru/js/puzzle/jquery-3.2.1.min.js, line 5: TypeError: document.querySelector(...) is null
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

        Numbers().fillForm(inputA, "100", inputB, "100", inputC, "100", submit)
        result = driver.findElement(By.cssSelector(".info")).text
        assertThat(result, `is`("Это равносторонний треугольник.\nВы ввели:\nA: 100; B: 100; C: 100"))

        Numbers().fillForm(inputA, "10", inputB, "10", inputC, "5", submit)
        result = driver.findElement(By.cssSelector(".info")).text
        assertThat(result, `is`("Это равнобедренный треугольник.\nВы ввели:\nA: 10; B: 10; C: 5"))

        Numbers().fillForm(inputA, "10", inputB, "10", inputC, "0", submit)
        result = driver.findElement(By.cssSelector(".error")).text
        assertThat(result, `is`("Одна сторона больше суммы двух других или равна ей.\nВы ввели:\nA: 10; B: 10; C: 0"))

        Numbers().fillForm(inputA, "9", inputB, "5", inputC, "6", submit)
        result = driver.findElement(By.cssSelector(".info")).text
        assertThat(result, `is`("Это тупоугольный треугольник.\nВы ввели:\nA: 9; B: 5; C: 6"))

        Numbers().fillForm(inputA, "3", inputB, "4", inputC, "5", submit)
        result = driver.findElement(By.cssSelector(".info")).text
        assertThat(result, `is`("Это прямоугольный треугольник.\nВы ввели:\nA: 3; B: 4; C: 5"))

        Numbers().fillForm(inputA, "3", inputB, "", inputC, "4", submit)
        result = driver.findElement(By.cssSelector(".error")).text
        assertThat(result, `is`("Задайте все стороны."))

        Numbers().fillForm(inputA, "-1", inputB, "4", inputC, "5", submit)
        result = driver.findElement(By.cssSelector(".error")).text
        assertThat(result, `is`("Это НЕ треугольник.\nВы ввели:\nA: -1; B: 4; C: 5"))

        Numbers().fillForm(inputA, "", inputB, "", inputC, "", submit)
        result = driver.findElement(By.cssSelector(".error")).text
        assertThat(result, `is`("Задайте все стороны."))

        Numbers().fillForm(inputA, "4", inputB, "6", inputC, "7", submit)
        result = driver.findElement(By.cssSelector(".info")).text
        assertThat(result, `is`("Это остроугольный треугольник.\nВы ввели:\nA: 4; B: 6; C: 7"))

        Numbers().fillForm(inputA, "<script>alert()</script>", inputB, "6", inputC, "7", submit)
        result = driver.findElement(By.cssSelector(".error")).text
        assertThat(result, `is`("XSS это плохо! Так не получится. :)"))

        Numbers().fillForm(inputA, "SELECT * FROM news WHERE id", inputB, "6", inputC, "7", submit)
        result = driver.findElement(By.cssSelector(".error")).text
        assertThat(result, `is`("SQL-инъекции это плохо! Так не получится. :)"))

        Numbers().fillForm(inputA, "2162176271261628172", inputB, "21", inputC, "21", submit)
        result = driver.findElement(By.cssSelector(".error")).text
        assertThat(result, `is`("Числа слишком большие.\nВы ввели:\nA: 2162176271261628172; B: 21; C: 21"))

        Numbers().fillForm(inputA, "0", inputB, "0", inputC, "0", submit)
        result = driver.findElement(By.cssSelector(".info")).text
        assertThat(result, `is`("Это равносторонний треугольник.\nВы ввели:\nA: 0; B: 0; C: 0"))

        Numbers().fillForm(inputA, "5.6", inputB, "5", inputC, "5.6", submit)
        result = driver.findElement(By.cssSelector(".error")).text
        assertThat(result, `is`("Это НЕ треугольник.\nВы ввели:\nA: 5.6; B: 5; C: 5.6"))

        Numbers().fillForm(inputA, "323", inputB, "122", inputC, "", submit)
        result = driver.findElement(By.cssSelector(".error")).text
        assertThat(result, `is`("Это НЕ треугольник.\nВы ввели:\nA: 323; B: 122; C: "))
    }

//    @AfterAll
//    fun driverClose() {
//        driver.close()
//    }
}