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
	//收件人输入栏
	public WebElement reveiverElement() throws Exception{
		element =driver.findElement(objectMap.getLocator("126mail.sendMailPage.recivername"));
		return element;
	}
	
	public By by_reveiverElement() throws Exception{
		locate =objectMap.getLocator("126mail.sendMailPage.recivername");
		return locate;
	}
	
	//主题输入栏
	public WebElement lettertitle() throws Exception{
		
		element = driver.findElement(objectMap.getLocator("126mail.sendMailPage.lettertitle"));
		return element;		
	}
	
	public By by_lettertitle() throws Exception{
		locate =objectMap.getLocator("126mail.sendMailPage.lettertitle");
		return locate;
	}
	
	//添加附件链接元素
	public WebElement attach() throws Exception{
		element = driver.findElement(objectMap.getLocator("126mail.sendMailPage.attach"));
		return element;
	}
	
	//上传附件操作
	public void upload(){
		 try {
			Thread.sleep(3000);
			Runtime.getRuntime().exec("uploadscripts/upload.exe"); //注意上传文件之后，需等待几秒否则后面的执行步骤容易出错    
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
	
	//写信内容输入框的iframe
	public WebElement lettercontentframe() throws Exception{
		
		element = driver.findElement(objectMap.getLocator("126mail.sendMailPage.lettercontent"));
		System.out.println("element_path="+objectMap.getLocator("126mail.sendMailPage.lettercontent"));
		return element;	
		
	}
	
	 //切入iframe
	public void switch_iframe(WebElement iframe){
		System.out.println("开始切入iframe");
		
		driver.switchTo().frame(iframe); //此处执行报错
		
		System.out.println("切入iframe成功");
	}
	
	public By by_lettercontent() throws Exception{
		locate =objectMap.getLocator("126mail.sendMailPage.lettercontent");
		return locate;
	}
	
	//写信
	public void writeletter(){		
		
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("document.getElementsByTagName('body')[0].innerHTML='<b>你好啊<b>'");
		    
		driver.switchTo().defaultContent();
	}
	
	//发送按钮
	public WebElement sendbutton() throws Exception{			
		element = driver.findElement(objectMap.getLocator("126mail.sendMailPage.sendbutton"));
		return element;		
	}
	
	public By by_sendbutton() throws Exception{
		locate =objectMap.getLocator("126mail.sendMailPage.sendbutton");
		return locate;
	}
	
	//智能等待点击元素
	public void element_wait(WebDriver driver,By locator,int scenods){
		driver=this.driver;
		WebDriverWait wait= new WebDriverWait(driver, scenods);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
			
	}
}
