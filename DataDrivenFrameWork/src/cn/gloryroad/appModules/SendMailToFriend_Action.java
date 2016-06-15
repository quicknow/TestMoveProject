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
		Log.info("���� Login_Action���execute����");
		Login_Action.execute(driver, userName, password);
		
		Thread.sleep(3000);
		Log.info("���Ե�¼���ҳ���Ƿ������δ���ʼ����Ĺؼ���");
		Assert.assertTrue(driver.getPageSource().contains("δ���ʼ�"));		
		
		HomePage homePage = new HomePage(driver);
		Log.info("�ڵ�¼����û���ҳ�У�������д�š�����");
		//�����10s��ʱ��ȴ�Ԫ���ܵ����ʱ���
		homePage.element_wait(driver, homePage.by_writemailbutton(),10);
		homePage.writemailbutton().click();
		
		SendMailPage sendmailpage = new SendMailPage(driver);
		Log.info("����3�룬�ȴ���д��ҳ��");		
		Thread.sleep(3000);	
		
		Log.info("�ռ���������������У����룺"+receiverName);
		sendmailpage.element_wait(driver, sendmailpage.by_reveiverElement(), 15);
		sendmailpage.reveiverElement().sendKeys(receiverName);
		
		Log.info("�������У����룺"+titleName);
		sendmailpage.element_wait(driver, sendmailpage.by_lettertitle(), 15);
		sendmailpage.lettertitle().sendKeys(titleName);
		
		Log.info("�����Ӹ���");
		sendmailpage.element_wait(driver, sendmailpage.by_attach(), 15);
		sendmailpage.attach().click();		
		//�ϴ�����
		sendmailpage.upload();
		
		//����iframe
//		Log.info("����iframe");	
//		sendmailpage.switch_iframe(sendmailpage.lettercontentframe());
//		
//		Log.info("д�ſ�����������");	
//		Thread.sleep(3000);
//		sendmailpage.writeletter();
		
		Log.info("������Ͱ�ť");	
		sendmailpage.element_wait(driver, sendmailpage.by_sendbutton(), 15);
		sendmailpage.sendbutton().click();
	}
		

}
