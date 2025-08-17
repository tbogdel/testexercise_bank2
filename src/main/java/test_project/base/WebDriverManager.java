package test_project.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class WebDriverManager {
    public static WebDriver initializeWebDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\WebDriver\\chromedriver.exe");
        System.setProperty("allure.results.directory", "target\\allure-results");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        //io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver(options);

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }
}
