import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.*
import org.openqa.selenium.WebDriver
import utils.TestUtils
import org.junit.jupiter.api.TestInstance.Lifecycle

@TestInstance(Lifecycle.PER_CLASS)
class ExampleUiTest {
    private val driver: WebDriver = TestUtils().configureFirefoxWebDriver(TestUtils().getCurrentWorkingDirectory())

    @Test
    fun checkDriver() {
        driver.get("https://www.google.com/")
        assertThat(driver.currentUrl, `is`("https://www.google.com/"))
    }

    @AfterAll
    fun driverClose() {
        driver.close()
    }
}