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

import cn.gloryroad.appModules.AddContactPerson_Action;
import cn.gloryroad.appModules.Login_Action;
import cn.gloryroad.util.*;

public class TestMail126AddContactPerson {
	public WebDriver driver;
	//调用Constant类中的常量Constant.Url
	private String baseUrl = Constant.Url;
	
	
	//定义dataProvider,并命名testData
	@DataProvider(name="新建联系人用例")
	public static Object[][] data() throws Exception{
		return ExcelUtil.getTestData(Constant.TestDataExcelFilePath, Constant.TestDataExcelFileSheet,"新建联系人");
		//DataProvider.class.getName();
	}
	
	@Test(dataProvider="新建联系人用例")
	public void testAddressBook(String CaseRowNumber, String testCaseName,String mailUserName,
			String mailPassWord,String contactPersonName,String contactPersonEmail,
			String contactPersonMobile,String assertContactPersonName,
			String assertContactPersonEmail,String assertContactPersonMobile) throws Exception{
		
		Log.startTestCase(testCaseName);
		
		driver.get(baseUrl);
		
		Log.info("调用AddContactPerson_Action 类的 execute方法");
		
		try{
			AddContactPerson_Action.execute(driver, mailUserName, mailPassWord, contactPersonName, contactPersonEmail, contactPersonMobile);
		} catch(AssertionError error){
			Log.info("添加联系人失败");
			ExcelUtil.setCellData(Integer.parseInt(CaseRowNumber.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "测试执行失败");
			Assert.fail("执行AddContactPerson_Action类的execute方法后，休眠3秒");				
		}
		
		Log.info("调用AddContactPerson_Action类的execute方法后，休眠3秒");
		Thread.sleep(3000);
		
		Log.info("断言通讯录的页面是否包含联系人姓名的关键字");		
		try{
			Assert.assertTrue(driver.getPageSource().contains(assertContactPersonName));
		} catch(AssertionError error){
			Log.info("断言通讯录的页面是否包含联系人姓名的关键字失败");
			ExcelUtil.setCellData(Integer.parseInt(CaseRowNumber.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "测试执行失败");
			Assert.fail("断言通讯录的页面是否包含联系人姓名的关键字失败");
		}
		Log.info("断言通讯录的页面是否包含联系人电子邮件地址的关键字");
		
		try{
			Assert.assertTrue(driver.getPageSource().contains(assertContactPersonEmail));
		} catch(AssertionError error){
			Log.info("断言通讯录的页面是否包含联系人姓名的电子邮件地址失败");
			ExcelUtil.setCellData(Integer.parseInt(CaseRowNumber.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "测试执行失败");
			Assert.fail("断言通讯录的页面是否包含联系人姓名的电子邮件地址失败");
		}
		
		Log.info("断言通讯录的页面是否包含联系人手机号的关键字");
		
		try{
			Assert.assertTrue(driver.getPageSource().contains(assertContactPersonMobile));
		} catch(AssertionError error){
			Log.info("断言通讯录的页面是否包含联系人手机号的关键字失败");
			ExcelUtil.setCellData(Integer.parseInt(CaseRowNumber.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "测试执行失败");
			Assert.fail("断言通讯录的页面是否包含联系人手机号的关键字失败");
		}
		
		Log.info("新建联系人的全部断言成功，在Excel的测试数据文件的“测试执行结果”列中写入“执行成功”");
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
