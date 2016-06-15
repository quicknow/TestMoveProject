package cn.gloryroad.util;

import java.io.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.By;

public class ObjectMap {
	//初始化将配置文件读入内存中
	Properties properties;
	public ObjectMap(String propFile){
		
		 properties = new Properties();
		try{
			System.out.println(propFile);
			//InputStream in = new BufferedInputStream (new FileInputStream(propFile));
			FileInputStream in = new FileInputStream(propFile);
			properties.load(in);		
			
			in.close();
		} catch(IOException e){
			System.out.println("读取对象文件出错");
			e.printStackTrace();
		}
	}
	
	//通过name-value的键值对 获取元素定位的位置
	public By getLocator(String ElementNameInpropFile) throws Exception{
		String locator = properties.getProperty(ElementNameInpropFile);
		//locator字符串中‘>’左边的字符串是定位的类型
		String locatorType = locator.split(">")[0];
		//locator字符串中‘>’右边的是定位的表达式
		String locatorValue= locator.split(">")[1];
		
		
		/*在Eclipse中的配置文件均默认为ISO-8859-1编码存储，使用getBytes方法可以将字符串编码转换为U
		 * TF-8编码，以此来解决在配置文件读取中文乱码的问题
		 */
		//locatorValue = new String(locatorValue1.getBytes("ISO-8859-1"),"UTF-8"); 经过测试验证发现不需要转
		
		//System.out.println("能否打印中文呢？请看："+locatorValue);
		//locatorValue = new String(locatorValue.getBytes("ISO-8859-1"),"GBK");
		System.out.println("获取的定位类型："+ locatorType+"\t 获取的定位表达式"+locatorValue);
		
		if(locatorType.toLowerCase().equals("id"))
			return By.id(locatorValue);
		else if(locatorType.toLowerCase().equals("name"))
			return By.name(locatorValue);
		else if((locatorType.toLowerCase().equals("classname"))||(locatorType.toLowerCase().equals("class")))
			return By.className(locatorValue);
		else if((locatorType.toLowerCase().equals("tagname"))||(locatorType.toLowerCase().equals("tag")))
			return By.tagName(locatorValue);
		else if((locatorType.toLowerCase().equals("linkText"))||(locatorType.toLowerCase().equals("link")))
			return By.linkText(locatorValue);
		else if(locatorType.toLowerCase().equals("partiallinktext"))
			return By.partialLinkText(locatorValue);
		else if((locatorType.toLowerCase().equals("cssselector"))||(locatorType.toLowerCase().equals("css")))
			return By.cssSelector(locatorValue);
		else if(locatorType.toLowerCase().equals("xpath"))
			return By.xpath(locatorValue);
		else
			throw new Exception("输入的locator type 未在程序中定义："+locatorType);		
	}
}
