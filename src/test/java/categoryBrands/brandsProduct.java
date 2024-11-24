package categoryBrands;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class brandsProduct {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void launchHomepage() {
        //Section: Opens homepage & verifies it loaded correctly
        driver.get("https://automationexercise.com/");
        try {
            WebElement consentButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Consent']")));
            consentButton.click();
        } catch (Exception e) {
            System.out.println("Consent button not displayed");
        }
        String pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, "Automation Exercise");
    }

    @Test(priority = 2)
    public void productPage() {
        // Opens product page
        driver.findElement(By.xpath("//header/div[1]/div[1]/div[1]/div[2]/div[1]/ul[1]/li[2]/a[1]")).click();
        // scrolls down
        WebElement brandsOption = driver.findElement(By.xpath("//body/section[2]/div[1]/div[1]/div[2]/div[1]/div[5]/div[1]/div[1]/div[1]/img[1]"));
        Actions scrollAction = new Actions(driver);
        // scrollAction.moveToElement(brandsOption).perform();
        scrollAction.moveToElement(brandsOption).perform();
        String pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, "Automation Exercise - All Products");
    }

    @Test(priority = 3)
    public void brandsView() {
        // clicks ob polo brand
        driver.findElement(By.xpath("//span[contains(text(),'(6)')]")).click();
        // verifies the page loaded correctly
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://automationexercise.com/brand_products/Polo");
        // verifies product is displayed
        List<WebElement> poloBrand = driver.findElements(By.className("product-image-wrapper"));
        Assert.assertFalse(poloBrand.isEmpty());

        // Clicks on H&M brand
        driver.findElement(By.xpath("//body/section[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/ul[1]/li[2]/a[1]/span[1]")).click();
        // verifies the page loaded correctly
        String currentUrl2 = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl2, "https://automationexercise.com/brand_products/H&M");
        // verify products are displayed
        List<WebElement> hmBrand = driver.findElements(By.className("product-image-wrapper"));
        Assert.assertFalse(hmBrand.isEmpty());
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

}
