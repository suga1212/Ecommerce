package ecommerce;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class EcommerceTesting {

    private static final String URL = "https://www.demoblaze.com/";
    private static WebDriver driver;


    public static void initializeDriver() {
    	 ChromeOptions options = new ChromeOptions();
         options.addArguments("--remote-debugging-port=9222");
    	WebDriverManager.chromedriver().setup();
    	driver = new ChromeDriver(options);
    	driver.manage().window().maximize();
    }
    
    public static void testHomePage() {
        driver.get(URL);
        System.out.println("Home Page loaded successfully.");
        String title = driver.getTitle();
        if (title.contains("STORE")) {
            System.out.println("Title verification passed.");
        } else {
            System.out.println("Title verification failed.");
        }
    }

    public static void testProductBrowsing() {

        driver.findElement(By.xpath("//a[@class='list-group-item'][contains(text(),'Phones')]")).click();
      
    }

    public static void testAddToCart() throws InterruptedException {
    	 Thread.sleep(5000);
        driver.findElement(By.linkText("Samsung galaxy s6")).click();
        Thread.sleep(2000);
        driver.findElement(By.linkText("Add to cart")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        System.out.println("Product added to cart successfully.");
    }

    public static void testCheckoutProcess() throws InterruptedException {
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

   
    public static void testOrderConfirmation() {
        WebElement confirmation = driver.findElement(By.className("sweet-alert"));
        System.out.println("Order Confirmation: " + confirmation.getText());

        WebElement receiptButton = driver.findElement(By.xpath("//button[text()='OK']"));
        receiptButton.click();
        System.out.println("Order confirmed.");
    }
    
    public static void main(String[] args) throws InterruptedException {
        initializeDriver();
        try {
            testHomePage();
            testProductBrowsing();
            testAddToCart();
            testCheckoutProcess();
            testOrderConfirmation();
        } finally {
            driver.quit();
        }
    }
}

