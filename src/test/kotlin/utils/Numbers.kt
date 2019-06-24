package utils

import org.openqa.selenium.WebElement

class Numbers {
    fun fillForm(
        inputA: WebElement, sendA: String,
        inputB: WebElement, sendB: String,
        inputC: WebElement, sendC: String,
        submit: WebElement
    ) {
        inputA.clear()
        inputB.clear()
        inputC.clear()
        inputA.sendKeys(sendA)
        inputB.sendKeys(sendB)
        inputC.sendKeys(sendC)
        Thread.sleep(2000)
        submit.click()
    }
}