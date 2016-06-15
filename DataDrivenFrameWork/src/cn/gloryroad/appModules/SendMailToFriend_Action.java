package cn.gloryroad.appModules;


import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.openqa.selenium.support.*;

import cn.gloryroad.pageObjects.AddressBookPage;
import cn.gloryroad.pageObjects.HomePage;
import cn.gloryroad.pageObjects.SendMailPage;
import cn.gloryroad.util.Log;

public class SendMailToFriend_Action {
	
	public static void execute(WebDriver driver,String userName,String password,String receiverName,String titleName) throws Exception{
		Log.info("调用 Login_Action类的execute方法");
		Login_Action.execute(driver, userName, password);
		
		Thread.sleep(3000);
		Log.info("断言登录后的页面是否包含“未读邮件”的关键字");
		Assert.assertTrue(driver.getPageSource().contains("未读邮件"));		
		
		HomePage homePage = new HomePage(driver);
		Log.info("在登录后的用户主页中，单击“写信”链接");
		//设置最长10s的时间等待元素能点击的时点击
		homePage.element_wait(driver, homePage.by_writemailbutton(),10);
		homePage.writemailbutton().click();
		
		SendMailPage sendmailpage = new SendMailPage(driver);
		Log.info("休眠3秒，等待打开写信页面");		
		Thread.sleep(3000);	
		
		Log.info("收件人姓名的输入框中，输入："+receiverName);
		sendmailpage.element_wait(driver, sendmailpage.by_reveiverElement(), 15);
		sendmailpage.reveiverElement().sendKeys(receiverName);
		
		Log.info("在主题中，输入："+titleName);
		sendmailpage.element_wait(driver, sendmailpage.by_lettertitle(), 15);
		sendmailpage.lettertitle().sendKeys(titleName);
		
		Log.info("点击添加附件");
		sendmailpage.element_wait(driver, sendmailpage.by_attach(), 15);
		sendmailpage.attach().click();		
		//上传附件
		sendmailpage.upload();
		
		//切入iframe
//		Log.info("切入iframe");	
//		sendmailpage.switch_iframe(sendmailpage.lettercontentframe());
//		
//		Log.info("写信框中输入内容");	
//		Thread.sleep(3000);
//		sendmailpage.writeletter();
		
		Log.info("点击发送按钮");	
		sendmailpage.element_wait(driver, sendmailpage.by_sendbutton(), 15);
		sendmailpage.sendbutton().click();
	}
		

}
