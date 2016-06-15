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
	//指定页面元素定位表达式配置文件的绝对文件路径
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
	
 //切入iframe
	public void switch_iframe(WebElement element){
		driver.switchTo().frame(element);
	}

 //返回登陆框的iframe的元素对象
	public WebElement get_login_iframe() throws Exception{
		element = driver.findElement(objectMap.getLocator("126mail.loginPage.getloginiframe"));
		System.out.println("iframe_path="+objectMap.getLocator("126mail.loginPage.getloginiframe"));
		return element;
	}
	


 //返回登陆页面中的用户名输入框页面元素对象
	public WebElement userName() throws Exception{
		//使用objectMap类中的getLocator方法获取配置文件中关于用户名的定位方式和定位表达式 126mail.loginPage.username
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
