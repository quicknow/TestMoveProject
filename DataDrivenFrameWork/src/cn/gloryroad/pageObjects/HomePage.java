package cn.gloryroad.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cn.gloryroad.util.ObjectMap;

public class HomePage {	
	
	private By locator=null;
	private WebElement element=null;	
	private ObjectMap objectMap=new ObjectMap("objectMap.properties");
	private WebDriver driver;
	
	public HomePage(WebDriver driver){
		this.driver = driver;		
	
	}
	
	   //获取登陆后主页中的“通讯录”链接的位置信息
	public By by_addressLink() throws Exception{
			
		locator = objectMap.getLocator("126mail.homePage.addressbook");
		return locator;
	}	
	
	
	//获取登陆后主页中的“通讯录”链接
	public WebElement addressLink() throws Exception{
		
		element = driver.findElement(objectMap.getLocator("126mail.homePage.addressbook"));
		return element;
	}
	
	//获取登陆后主页中的"写信"按钮的元素的位置信息
	public By by_writemailbutton() throws Exception{
		
		locator = objectMap.getLocator("126mail.homePage.writemail");
		return locator;
	}	
	
	//获取登陆后主页中的"写信"按钮的元素
	public WebElement writemailbutton() throws Exception{
		
		element = driver.findElement(objectMap.getLocator("126mail.homePage.writemail"));
		return element;
	}
	
	//智能等待点击元素
	public void element_wait(WebDriver driver,By locator,int scenods){
		driver=this.driver;
		WebDriverWait wait= new WebDriverWait(driver, scenods);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		
	}
	
}
