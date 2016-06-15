package cn.gloryorad.testScripts;

import java.util.*;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class sendmail {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://www.126.com";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testSendmail() throws Exception {
    driver.get(baseUrl);
    WebElement iframeElement=driver.findElement(By.id("x-URS-iframe"));
    driver.switchTo().frame(iframeElement);
    
    //driver.findElement(By.id("kw")).clear();
    driver.findElement(By.xpath("//div[@id='account-box']/div[2]/input")).sendKeys("testform1");

    driver.findElement(By.xpath("//input[@placeholder='密码']")).sendKeys("zwb888888");
    
    WebDriverWait wait= new WebDriverWait(driver, 15);
    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("dologin")));
    //driver.findElement(By.id("dologin")).click();
    WebElement loginbutton=driver.findElement(By.id("dologin"));
    JavaScriptClick(loginbutton);
    
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='写 信']")));
    driver.findElement(By.xpath("//span[text()='写 信']")).click();
    // class="nui-editableAddr-txt"
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@class='nui-editableAddr-ipt']")));
    driver.findElement(By.xpath("//input[@class='nui-editableAddr-ipt']")).sendKeys("xiaozhuzhu7586@126.com");
    //class="nui-ipt-input" type="text" maxlength="256" tabindex="1"
    
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@class='nui-ipt-input' and @type='text' and @maxlength='256' and  @tabindex='1']")));
    driver.findElement(By.xpath("//input[@class='nui-ipt-input' and @type='text' and @maxlength='256' and  @tabindex='1']")).sendKeys("端午快乐！");
    
    wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("添加附件")));
    driver.findElement(By.linkText("添加附件")).click();
    
    Thread.sleep(3000);
    Runtime.getRuntime().exec("uploadscripts/upload.exe"); //注意上传文件之后，需等待几秒否则后面的执行步骤容易出错    
    Thread.sleep(3000);
    
    WebElement iframe2=driver.findElement(By.className("APP-editor-iframe"));
    driver.switchTo().frame(iframe2);
    
    JavascriptExecutor js=(JavascriptExecutor)driver;
    js.executeScript("document.getElementsByTagName('body')[0].innerHTML='<b>你好啊"
    		+ "撒旦法萨芬是否23我的发士大夫<b>'");
    
    driver.switchTo().defaultContent();
    //Thread.sleep(3000); contains(.,'发送')
    Thread.sleep(2000);
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@id,'_mail_toolbar_')]/div[1]/div/span[2]")));    
    
    WebElement sendbutton=driver.findElement(By.xpath("//div[contains(@id,'_mail_toolbar_')]/div[1]/div/span[2]"));
    JavaScriptClick(sendbutton);
  }
  
  public void JavaScriptClick(WebElement element){
	  try {
		if(element.isEnabled()&&element.isDisplayed()){
			((JavascriptExecutor)driver).executeScript("arguments[0].click()", element);
		}else{
			System.out.println("元素不能点击");
		}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
  }

  @After
  public void tearDown() throws Exception {
    //driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }



  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
