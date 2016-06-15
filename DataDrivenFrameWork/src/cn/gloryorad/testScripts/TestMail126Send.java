package cn.gloryorad.testScripts;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import bsh.This;
import cn.gloryroad.appModules.*;
import cn.gloryroad.util.*;

public class TestMail126Send {
	public WebDriver driver;
	//调用Constant类中的常量Constant.Url
	private String baseUrl = Constant.Url;
	
	
	//定义dataProvider,并命名testData
	@DataProvider(name="testData")
	public static Object[][] data() throws Exception{
		return ExcelUtil.getTestData(Constant.TestDataExcelFilePath, Constant.TestDataExcelFileSheet,"sendmail");
	}
	
	@Test(dataProvider="testData")
	public void testAddressBook(String CaseRowNumber, String testCaseName,String userName,String password,
			String receiverName,String titleName,String assertText
			) throws Exception{
		
		Log.startTestCase(testCaseName);
		
		driver.get(baseUrl);
		
		Log.info("调用SendMailToFriend_Action 类的 execute方法");
		
		try{
			SendMailToFriend_Action.execute(driver, userName,password,receiverName,titleName);
		} catch(AssertionError error){
			Log.info("发送邮件失败");
			ExcelUtil.setCellData(Integer.parseInt(CaseRowNumber.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "测试执行失败");
			Assert.fail("执行SendMailToFriend_Action类的execute方法后，休眠3秒");				
		}
		
		Log.info("调用SendMailToFriend_Action类的execute方法后，休眠3秒");
		Thread.sleep(3000);
		
		Log.info("断言发送邮件的页面是否包含‘发送成功’的关键字");		
		try{
			Assert.assertTrue(driver.getPageSource().contains(assertText));
		} catch(AssertionError error){
			Log.info("发送邮件之后的页面不包含‘发送成功’");
			ExcelUtil.setCellData(Integer.parseInt(CaseRowNumber.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "测试执行失败");
			Assert.fail("发送邮件失败");
		}		
		
		Log.info("发送邮件的全部断言成功，在Excel的测试数据文件的“测试执行结果”列中写入“执行成功”");
		ExcelUtil.setCellData(Integer.parseInt(CaseRowNumber.split("[0]")[0]), ExcelUtil.getLastColumnNum(), "执行成功");
		Log.info("测试结果成功写入excel数据文件的“测试执行结果”列");
		Log.endTestCase(testCaseName);
	}
	
	
	
	@BeforeMethod
	public void beforeMethod(){
		
		//driver.manage().window().maximize();  在driver实例化之前加会报错误
		driver= new FirefoxDriver();
		//driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@AfterMethod
	public void afterMethod(){
		driver.quit();
	}
	
	@BeforeClass
	//使用Constand类中的常量，设定测试数据文件的文件路径和测试数据所在的Sheet名称
	public void BeforeClass() throws Exception{
		ExcelUtil.setExcelFile(Constant.TestDataExcelFilePath, Constant.TestDataExcelFileSheet);
		DOMConfigurator.configure("log4j.xml");
	}
	

}