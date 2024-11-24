package categoryBrands;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class productView {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    @Test(priority = 1)
    public void launchHomepage() {
        // Section: Opens homepage & verifies it loaded correctly
        driver.get("https://automationexercise.com/");
        try {
            WebElement consentButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Consent']")));
            consentButton.click();
        } catch (Exception e) {
            System.out.println("consent button not displayed");
        }

        String pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, "Automation Exercise");
        // Verifies category is visible
        List<WebElement> categoryProduct = driver.findElements(By.className("panel-heading"));
        Assert.assertFalse(categoryProduct.isEmpty());

    }

    @Test(priority = 2)
    public void categoryItems() {
        // Locates women category & click
        driver.findElement(By.xpath("//body/section[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/h4[1]/a[1]")).click();
        // clicks top in  women category
        WebElement womenCategory = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"Women\"]/div/ul/li[2]/a")));
        womenCategory.click();
        // Verifies the text "WOMEN TOP PRODUCTS"
        Wait<WebDriver> fluentwait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        WebElement topProductText = fluentwait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body/section[1]/div[1]/div[2]/div[2]/div[1]/h2[1]")));
        String actualText = topProductText.getText().trim().replaceAll("\\s+", " ");
        Assert.assertTrue(actualText.contains("WOMEN - TOPS PRODUCTS"));


    }

    @Test(priority = 3)
    public void categoryMen() {
        // Locates men category & click
        driver.findElement(By.xpath("//body/section[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/h4[1]/a[1]")).click();
        // clicks TShirts in men category
        driver.findElement(By.xpath("//a[contains(text(),'Tshirts')]")).click();
        // Verifies pageTitle
        String pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, "Automation Exercise - Tshirts Products");
    }

    @AfterClass
    public void tearDown() {
        // Closes browser
        driver.quit();
    }
}
