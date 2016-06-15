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
	//����Constant���еĳ���Constant.Url
	private String baseUrl = Constant.Url;
	
	
	//����dataProvider,������testData
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
		
		Log.info("����SendMailToFriend_Action ��� execute����");
		
		try{
			SendMailToFriend_Action.execute(driver, userName,password,receiverName,titleName);
		} catch(AssertionError error){
			Log.info("�����ʼ�ʧ��");
			ExcelUtil.setCellData(Integer.parseInt(CaseRowNumber.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "����ִ��ʧ��");
			Assert.fail("ִ��SendMailToFriend_Action���execute����������3��");				
		}
		
		Log.info("����SendMailToFriend_Action���execute����������3��");
		Thread.sleep(3000);
		
		Log.info("���Է����ʼ���ҳ���Ƿ���������ͳɹ����Ĺؼ���");		
		try{
			Assert.assertTrue(driver.getPageSource().contains(assertText));
		} catch(AssertionError error){
			Log.info("�����ʼ�֮���ҳ�治���������ͳɹ���");
			ExcelUtil.setCellData(Integer.parseInt(CaseRowNumber.split("[.]")[0]), ExcelUtil.getLastColumnNum(), "����ִ��ʧ��");
			Assert.fail("�����ʼ�ʧ��");
		}		
		
		Log.info("�����ʼ���ȫ�����Գɹ�����Excel�Ĳ��������ļ��ġ�����ִ�н��������д�롰ִ�гɹ���");
		ExcelUtil.setCellData(Integer.parseInt(CaseRowNumber.split("[0]")[0]), ExcelUtil.getLastColumnNum(), "ִ�гɹ�");
		Log.info("���Խ���ɹ�д��excel�����ļ��ġ�����ִ�н������");
		Log.endTestCase(testCaseName);
	}
	
	
	
	@BeforeMethod
	public void beforeMethod(){
		
		//driver.manage().window().maximize();  ��driverʵ����֮ǰ�ӻᱨ����
		driver= new FirefoxDriver();
		//driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@AfterMethod
	public void afterMethod(){
		driver.quit();
	}
	
	@BeforeClass
	//ʹ��Constand���еĳ������趨���������ļ����ļ�·���Ͳ����������ڵ�Sheet����
	public void BeforeClass() throws Exception{
		ExcelUtil.setExcelFile(Constant.TestDataExcelFilePath, Constant.TestDataExcelFileSheet);
		DOMConfigurator.configure("log4j.xml");
	}
	

}