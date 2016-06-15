package cn.gloryroad.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cn.gloryroad.util.ObjectMap;

public class SendMailPage  {
	private By locate = null;
	private WebElement element = null;
	private ObjectMap objectMap = new ObjectMap("objectMap.properties");
	private WebDriver driver;
	
	public SendMailPage (WebDriver driver){
		this.driver=driver;
	}
	//�ռ���������
	public WebElement reveiverElement() throws Exception{
		element =driver.findElement(objectMap.getLocator("126mail.sendMailPage.recivername"));
		return element;
	}
	
	public By by_reveiverElement() throws Exception{
		locate =objectMap.getLocator("126mail.sendMailPage.recivername");
		return locate;
	}
	
	//����������
	public WebElement lettertitle() throws Exception{
		
		element = driver.findElement(objectMap.getLocator("126mail.sendMailPage.lettertitle"));
		return element;		
	}
	
	public By by_lettertitle() throws Exception{
		locate =objectMap.getLocator("126mail.sendMailPage.lettertitle");
		return locate;
	}
	
	//��Ӹ�������Ԫ��
	public WebElement attach() throws Exception{
		element = driver.findElement(objectMap.getLocator("126mail.sendMailPage.attach"));
		return element;
	}
	
	//�ϴ���������
	public void upload(){
		 try {
			Thread.sleep(3000);
			Runtime.getRuntime().exec("uploadscripts/upload.exe"); //ע���ϴ��ļ�֮����ȴ������������ִ�в������׳���    
			 Thread.sleep(3000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	
	public By by_attach() throws Exception{
		locate =objectMap.getLocator("126mail.sendMailPage.attach");
		return locate;
	}
	
	//д������������iframe
	public WebElement lettercontentframe() throws Exception{
		
		element = driver.findElement(objectMap.getLocator("126mail.sendMailPage.lettercontent"));
		System.out.println("element_path="+objectMap.getLocator("126mail.sendMailPage.lettercontent"));
		return element;	
		
	}
	
	 //����iframe
	public void switch_iframe(WebElement iframe){
		System.out.println("��ʼ����iframe");
		
		driver.switchTo().frame(iframe); //�˴�ִ�б���
		
		System.out.println("����iframe�ɹ�");
	}
	
	public By by_lettercontent() throws Exception{
		locate =objectMap.getLocator("126mail.sendMailPage.lettercontent");
		return locate;
	}
	
	//д��
	public void writeletter(){		
		
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("document.getElementsByTagName('body')[0].innerHTML='<b>��ð�<b>'");
		    
		driver.switchTo().defaultContent();
	}
	
	//���Ͱ�ť
	public WebElement sendbutton() throws Exception{			
		element = driver.findElement(objectMap.getLocator("126mail.sendMailPage.sendbutton"));
		return element;		
	}
	
	public By by_sendbutton() throws Exception{
		locate =objectMap.getLocator("126mail.sendMailPage.sendbutton");
		return locate;
	}
	
	//���ܵȴ����Ԫ��
	public void element_wait(WebDriver driver,By locator,int scenods){
		driver=this.driver;
		WebDriverWait wait= new WebDriverWait(driver, scenods);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
			
	}
}
