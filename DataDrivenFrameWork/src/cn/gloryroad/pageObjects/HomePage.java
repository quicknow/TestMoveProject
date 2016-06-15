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
	
	   //��ȡ��½����ҳ�еġ�ͨѶ¼�����ӵ�λ����Ϣ
	public By by_addressLink() throws Exception{
			
		locator = objectMap.getLocator("126mail.homePage.addressbook");
		return locator;
	}	
	
	
	//��ȡ��½����ҳ�еġ�ͨѶ¼������
	public WebElement addressLink() throws Exception{
		
		element = driver.findElement(objectMap.getLocator("126mail.homePage.addressbook"));
		return element;
	}
	
	//��ȡ��½����ҳ�е�"д��"��ť��Ԫ�ص�λ����Ϣ
	public By by_writemailbutton() throws Exception{
		
		locator = objectMap.getLocator("126mail.homePage.writemail");
		return locator;
	}	
	
	//��ȡ��½����ҳ�е�"д��"��ť��Ԫ��
	public WebElement writemailbutton() throws Exception{
		
		element = driver.findElement(objectMap.getLocator("126mail.homePage.writemail"));
		return element;
	}
	
	//���ܵȴ����Ԫ��
	public void element_wait(WebDriver driver,By locator,int scenods){
		driver=this.driver;
		WebDriverWait wait= new WebDriverWait(driver, scenods);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		
	}
	
}
