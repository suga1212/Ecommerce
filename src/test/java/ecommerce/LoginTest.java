package ecommerce;


import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class LoginTest {
    WebDriver driver;
    ExtentTest test;
    ExtentReports extent;
    

    @BeforeClass
    public void setup() {
    	extent = ExtentReportManager.getInstance();
        driver = BrowserFactory.getBrowser("chrome");
        driver.get("https://www.demoblaze.com/");
        screenShot(); 
    }

    public void testProductBrowsing() {

        driver.findElement(By.xpath("//a[@class='list-group-item'][contains(text(),'Phones')]")).click();
        screenShot();
    }

    public void testAddToCart() throws InterruptedException {
    	 Thread.sleep(5000);
        driver.findElement(By.linkText("Samsung galaxy s6")).click();
        Thread.sleep(2000);
        driver.findElement(By.linkText("Add to cart")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        System.out.println("Product added to cart successfully.");
        screenShot();
    }

    public void testCheckoutProcess() throws InterruptedException {
        driver.findElement(By.id("cartur")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//button[text()='Place Order']")).click();

        
        Thread.sleep(3000);
        driver.findElement(By.id("name")).sendKeys("John Doe");
        driver.findElement(By.id("country")).sendKeys("USA");
        driver.findElement(By.id("city")).sendKeys("New York");
        driver.findElement(By.id("card")).sendKeys("4111111111111111");
        driver.findElement(By.id("month")).sendKeys("12");
        driver.findElement(By.id("year")).sendKeys("2025");

        
        driver.findElement(By.xpath("//button[text()='Purchase']")).click();
        Thread.sleep(3000);
        System.out.println("Checkout process completed.");
    }

   
    public void testOrderConfirmation() {
        WebElement confirmation = driver.findElement(By.className("sweet-alert"));
        System.out.println("Order Confirmation: " + confirmation.getText());
        screenShot();
        WebElement receiptButton = driver.findElement(By.xpath("//button[text()='OK']"));
        receiptButton.click();
        System.out.println("Order confirmed.");
        screenShot();
    }
    @Test
    public void ecommerce() throws InterruptedException {
        try {
            testProductBrowsing();
            testAddToCart();
            testCheckoutProcess();
            testOrderConfirmation();
        } finally {
            driver.quit();
            extent.flush();
        }
    }
    @AfterClass
    public void tearDown() {
        driver.quit();
        extent.flush();
    }
    

    @AfterMethod
    public void captureScreenshotOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = ScreenshotUtil.takeScreenshot(driver, result.getName());
            test.fail("Test failed: " + result.getThrowable())
                .addScreenCaptureFromPath(screenshotPath);
        }
    }
    
    public void screenShot() {
    	test = extent.createTest("Ecommerce");
        String screenshotPath = ScreenshotUtil.takeScreenshot(driver, "screenshot");
        test.pass("Ecommerce").addScreenCaptureFromPath(screenshotPath);
    }

}

