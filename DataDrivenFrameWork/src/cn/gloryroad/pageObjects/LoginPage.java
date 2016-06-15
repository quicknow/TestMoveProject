package cn.gloryroad.pageObjects;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;

import com.gargoylesoftware.htmlunit.javascript.host.Element;

import cn.gloryroad.util.*;


public class LoginPage {
	private WebElement element = null;
	//ָ��ҳ��Ԫ�ض�λ���ʽ�����ļ��ľ����ļ�·��
	private ObjectMap objectMap = new ObjectMap("objectMap.properties");
	
	public ObjectMap getObjectMap() {
		return objectMap;
	}

	public void setObjectMap(ObjectMap objectMap) {
		this.objectMap = objectMap;
	}

	private WebDriver driver;
	
	public LoginPage(WebDriver driver){
		this.driver = driver;
	}	
	
 //����iframe
	public void switch_iframe(WebElement element){
		driver.switchTo().frame(element);
	}

 //���ص�½���iframe��Ԫ�ض���
	public WebElement get_login_iframe() throws Exception{
		element = driver.findElement(objectMap.getLocator("126mail.loginPage.getloginiframe"));
		System.out.println("iframe_path="+objectMap.getLocator("126mail.loginPage.getloginiframe"));
		return element;
	}
	


 //���ص�½ҳ���е��û��������ҳ��Ԫ�ض���
	public WebElement userName() throws Exception{
		//ʹ��objectMap���е�getLocator������ȡ�����ļ��й����û����Ķ�λ��ʽ�Ͷ�λ���ʽ 126mail.loginPage.username
		element = driver.findElement(objectMap.getLocator("126mail.loginPage.username"));
		return element;
	}
	
	public WebElement password() throws Exception{
		element = driver.findElement(objectMap.getLocator("126mail.loginPage.password"));
		return element;
	}
	
	public WebElement loginButton() throws Exception{
		element= driver.findElement(objectMap.getLocator("126mail.loginPage.loginbutton"));
		return element;
	}
}
