package cn.gloryroad.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;
import cn.gloryroad.util.ObjectMap;

public class AddressBookPage {
	private WebElement element = null;
	private ObjectMap objectMap = new ObjectMap("objectMap.properties");
	private WebDriver driver;
	
	public AddressBookPage(WebDriver driver){
		this.driver=driver;
	}
	//获取新建联系人按钮
	public WebElement createContactPersonButton() throws Exception{
		element =driver.findElement(objectMap.getLocator("126mail.addressBook.createContactPerson"));
		return element;
	}
	//获取新建联系人界面中的姓名输入框
	public WebElement contactPersonName() throws Exception{
		
		element = driver.findElement(objectMap.getLocator("126mail.addressBook.contactPessonName"));
		return element;		
	}
	
	//获取新建联系人界面中的电子邮件输入框
	public WebElement contactPersonEmail() throws Exception{
		element = driver.findElement(objectMap.getLocator("126mail.addressBook.contactPersonEmail"));
		return element;
	}
	//获取新建联系人界面中的手机号输入框
	public WebElement contactPersonMobile() throws Exception{
		
		element = driver.findElement(objectMap.getLocator("126mail.addressBook.contactPersonMobile"));
		return element;		
	}
	
	//获取新建联系人界面中保存信息的“确定”按钮
	public WebElement saveButton() throws Exception{
			
		element = driver.findElement(objectMap.getLocator("126mail.addressBook.saveButton"));
		return element;		
	}
	
}
