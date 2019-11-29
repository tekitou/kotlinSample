package org.tekitou.kotlin.kotlinSample.controller

import io.micrometer.core.annotation.Timed
import io.micrometer.core.instrument.MeterRegistry
import mu.KLogging
import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxBinary
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.tekitou.kotlin.kotlinSample.settings.HelloSettings
import java.util.concurrent.TimeUnit


@Controller
@RequestMapping
class FireFoxController(meterRegistry: MeterRegistry, helloSettings: HelloSettings) {
    @Timed
    @GetMapping("firefox")
    @ResponseBody
    fun hello(): String {
        val firefoxBinary = FirefoxBinary()
        firefoxBinary.addCommandLineOptions("--headless")
        System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver")
        val firefoxOptions = FirefoxOptions()
        firefoxOptions.binary = firefoxBinary
        val driver = FirefoxDriver(firefoxOptions)
        try {
            driver.get("http://www.google.com")
            driver.manage().timeouts().implicitlyWait(
                20,
                TimeUnit.SECONDS
            )
            val queryBox = driver.findElement(By.name("q"))
            queryBox.sendKeys("headless firefox")
            val searchBtn = driver.findElement(By.name("btnK"))
            val wait = WebDriverWait(driver, 10)
            wait.until(ExpectedConditions.elementToBeClickable(searchBtn))
            searchBtn.click()
            val srgDiv = driver.findElement(By.id("search"))
            srgDiv.findElements(By.tagName("a"))[0].click()
            println(driver.pageSource)
            return driver.pageSource;
        } finally {
            driver.quit()
        }
    }

    companion object : KLogging()
}
