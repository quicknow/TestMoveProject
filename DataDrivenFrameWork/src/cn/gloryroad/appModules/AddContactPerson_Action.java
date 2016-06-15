package cn.gloryroad.appModules;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.openqa.selenium.support.*;

import cn.gloryroad.pageObjects.AddressBookPage;
import cn.gloryroad.pageObjects.HomePage;
import cn.gloryroad.util.Log;

public class AddContactPerson_Action {
	
	public static void execute(WebDriver driver,String userName,String password,String contactName,String contactEmail,String contactMobile) throws Exception{
		Log.info("调用 Login_Action类的execute方法");
		Login_Action.execute(driver, userName, password);
		
		Thread.sleep(3000);
		Log.info("断言登录后的页面是否包含“未读邮件”的关键字");
		Assert.assertTrue(driver.getPageSource().contains("未读邮件"));		
		
		HomePage homePage = new HomePage(driver);
		Log.info("在登录后的用户主页中，单击“通讯录”链接");
		homePage.addressLink().click();
		
		AddressBookPage addressBookPage = new AddressBookPage(driver);
		Log.info("休眠3秒，等待打开通讯录页面");		
		Thread.sleep(3000);	
		addressBookPage.createContactPersonButton().click();
		
		Thread.sleep(1000);
		Log.info("在联系人姓名的输入框中，输入："+contactName);		
		addressBookPage.contactPersonName().sendKeys(contactName);
		
		Log.info("在联系人电子邮件的输入框中，输入："+contactEmail);
		addressBookPage.contactPersonEmail().sendKeys(contactEmail);
		
		Log.info("在联系人手机号的输入框中，输入："+contactMobile);
		addressBookPage.contactPersonMobile().sendKeys(contactMobile);
		
		Log.info("在联系人手机号的输入框输入手机号后，单击“确定”按钮");
		addressBookPage.saveButton().click();
		Log.info("休眠5秒，等待保存联系人后返回到通讯录的主页面");
		Thread.sleep(5000);		
	}
		

}
