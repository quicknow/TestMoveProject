package cn.gloryroad.appModules;

import org.openqa.selenium.WebDriver;

import cn.gloryroad.pageObjects.LoginPage;

import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cn.gloryroad.util.Log;
import cn.gloryroad.util.*;


public class Login_Action {
	
	public static void execute(WebDriver driver, String userName, String passWord) throws Exception{
		
		//Log.info("������ַ http://mail.126.com");
		driver.get("http://mail.126.com");
		
		LoginPage loginPage = new LoginPage(driver);
		
		//�����½���iframe
		Log.info("�����½���������iframe");
		loginPage.switch_iframe(loginPage.get_login_iframe());
		
		Log.info("��126�����¼ҳ����û������������"+userName);
		loginPage.userName().sendKeys(userName);
		
		Log.info("��126�����¼ҳ����������������"+passWord);
		loginPage.password().sendKeys(passWord);
		
		Log.info("�ȴ�1���׼�������¼��ť"); 
		//Thread.sleep(3000); //�˴���û������ܵȴ�
		WebDriverWait wait=new WebDriverWait(driver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated(loginPage.getObjectMap().getLocator("126mail.loginPage.loginbutton")));
		
		
		Log.info("������¼ҳ��ĵ�¼��ť");
		loginPage.loginButton().click();
		
		Log.info("�ڵ�����¼��ť������5�룬�ȴ��ӵ�¼ҳ��ת����¼����û���ҳ");
		Thread.sleep(5000);
		//�л���ԭ����driver
		driver.switchTo().defaultContent();
	}

}
