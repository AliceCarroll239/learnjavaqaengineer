package page

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindAll
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import java.util.concurrent.TimeUnit

class Page(driver: WebDriver) {

    init {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES)
        PageFactory.initElements(driver, this)
    }

    @FindBy(css = ".js_a")
    lateinit var inputA: WebElement

    @FindBy(css = ".js_b")
    lateinit var inputB: WebElement

    @FindBy(css = ".js_c")
    lateinit var inputC: WebElement

    @FindBy(css = ".btn-submit")
    lateinit var submit: WebElement

    @FindBy(css = "[onclick]")
    lateinit var minimizeButton: WebElement

    fun minimizeButtonClick() {
        minimizeButton.click()
    }

}