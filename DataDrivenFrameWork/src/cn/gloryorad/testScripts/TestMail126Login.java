package cn.gloryorad.testScripts;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.support.*;

import cn.gloryroad.pageObjects.LoginPage;
import cn.gloryroad.util.Constant;
import cn.gloryroad.util.ExcelUtil;
import cn.gloryroad.appModules.Login_Action;


public class TestMail126Login {
	
		public WebDriver driver;
		String baseUrl="http://mail.126.com/";
		@Test
		public void testMailLogin() throws Exception{
			/* 登陆动作使用封装函数
			driver.get(baseUrl+"/");
			LoginPage loginPage = new LoginPage(driver);
			loginPage.userName().sendKeys("testman1987");
			loginPage.password().sendKeys("wulaoshi1978");			
			loginPage.loginButton().click();*/
			
			//执行测试
			Login_Action.execute(driver, "testform1", "zwb888888");
			Thread.sleep(5000);
			//判定结果
			Assert.assertTrue(driver.getPageSource().contains("未读邮件"));		
			
		}
		
		@BeforeMethod
		public void beforeMethod(){
			
			driver=new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);			
		}
		
		@AfterMethod
		public void afterMethod(){
			driver.quit();
		}
		
		 @BeforeClass
		public void beforeclass(){
			  try {
				ExcelUtil.setExcelFile(Constant.TestDataExcelFilePath,Constant.TestDataExcelFileSheet);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  DOMConfigurator.configure("log4j.xml");
		}		
	

}
