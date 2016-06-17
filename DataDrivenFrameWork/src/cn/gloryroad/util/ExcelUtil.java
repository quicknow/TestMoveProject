package cn.gloryroad.util;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import bsh.This;

public  class ExcelUtil {
	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;
	private static XSSFRow Row;
	
	public static void setExcelFile(String Path, String SheetName) throws IOException {
		
		FileInputStream ExcelFile;
		try{
			//实例化Excel文件的FileInputStream对象
			ExcelFile = new FileInputStream(Path);
			
			//实例化Excel文件中的XSSFWorkbook对象
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			/*
			 * 实例化XSSFSheet对象，指定Excel文件中的Sheet名称，后续用于Sheet中行、列和单元格的操作
			 */
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
		} catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	//读取Excel文件指定单元格的函数
	public static String getCellData(int RowNum, int ColNum) throws Exception{
		try{
			//通过函数参数指定单元格的行号和列号，获取指定的单元格对象
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			/*
			 * 如果单元格的内容为字符串类型，则使用getStringCellValue方法获取单元格的内容
			 * 如果单元格的内容为数字类型，则使用getNumericeCellValue()方法获取单元格的内容
			 * 注意getNumericeCellValue方法返回值为double类型，必须转换字符串类型
			 * 在Cell.getNumericeCellValue()前面增加“”,用于强制转换double类型到
			 * String类型,不加“” 则会抛出double类型无法转换到String类型的异常 :String.valueOf(Math.round(Cell.getNumericCellValue()
			 */
			String CellData=Cell.getCellType()==XSSFCell.CELL_TYPE_STRING? Cell
					.getStringCellValue()+""
					:String.valueOf(Math.round(Cell.getNumericCellValue()));
			return CellData;
		} catch(Exception e){
			return "";
		}
	}
	
	public static void setCellData(int RowNum,int ColNum, String Result) throws Exception{
		try{
			Row = ExcelWSheet.getRow(RowNum);
			Cell = Row.getCell(ColNum,Row.RETURN_BLANK_AS_NULL);
			
			if(Cell == null){
				Cell = Row.createCell(ColNum);
				
				Cell.setCellValue(Result);
			} else{
				Cell.setCellValue(Result);
			}
			
			FileOutputStream fileOut = new FileOutputStream(Constant.TestDataExcelFilePath);
			ExcelWBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}	
		
	
	//在Excel的指定Sheet中，获取第一次包含指定测试用例序号文字的行号
		public static int getFirstRowContainsTestCaseID(String sheetName,String testCaseName,int colNum){
			
			int i;
			try {
				ExcelWSheet = ExcelWBook.getSheet(sheetName);
				int rowCount = ExcelUtil.getRowCount(sheetName);
				for(i=0;i<rowCount;i++){
					//使用循环的方法遍历测试用例序号列的所有行，判断是否包含某个测试用例序号关键字
					if(ExcelUtil.getCellData(sheetName,i,colNum).equalsIgnoreCase(testCaseName)){
						//如果包含，则退出for循环，并返回包含测试用例序号关键字的行号
						break;
					}
				}
				
				return i;
			} catch (Exception e) {
								
				e.printStackTrace();
				//读取遇到异常，则返回空字符串
				return 0;
			}
		}
		
		//获取指定Sheet中某个测试用例步骤的个数
		public static int getTestCaseLastStepRow(String SheetName,String testCaseName,int testCaseStartRowNumber) {
			int number=testCaseStartRowNumber;
			try {
				ExcelWSheet = ExcelWBook.getSheet(SheetName);
				/*
				 * 从包含指定测试用例序号的第一行开始逐行遍历，直到某一行不出现指定测试用例序号
				 * 此时的遍历测试就是此测试用例步骤的个数
				 * 
				 */
				
				for(int i=testCaseStartRowNumber;i<ExcelUtil.getRowCount(SheetName)+1;i++){
					number=i;
					
					if(!testCaseName.equals(ExcelUtil.getCellData(SheetName,i,1))){		
						
						return number-1;
					}
				}				
				
				return number;
			} catch (Exception e) {
										
				e.printStackTrace();
				//读取遇到异常，则返回空字符串
				return 0;
			}
		}
		
		
	public static Object[][] getTestData(String excelFilePath,String sheetName,String testCaseName) throws Exception{
			
			File file= new File(excelFilePath);
			
			FileInputStream fins = new FileInputStream(file);
			
			Workbook Workbook = null;
			String fileExtensionName= excelFilePath.substring(excelFilePath.indexOf("."));
			
			if(fileExtensionName.equals(".xlsx")){
				Workbook = new XSSFWorkbook(fins);
			} else if(fileExtensionName.equals(".xls")){
				Workbook = new HSSFWorkbook(fins);
			}
			
			
			Sheet Sheet = Workbook.getSheet(sheetName);
			
			//用例名开始的行号
			int startRowCount=getFirstRowContainsTestCaseID(sheetName, testCaseName, 1);
			System.out.println("startRowCount="+startRowCount);
			//用例名结束的行号
			int endRowCount=getTestCaseLastStepRow(sheetName, testCaseName, startRowCount);		
			System.out.println("endRowCount="+endRowCount);
			List<Object[]> records = new ArrayList<Object[]>();
			
			for(int i = startRowCount;i<endRowCount+1;i++){
				
				Row row = Sheet.getRow(i);
				
				String fields[]=new String[row.getLastCellNum()-2];
				
				//String.valueOf(Math.round(Cell.getNumericCellValue()))
				if(row.getCell(row.getLastCellNum()-2).getStringCellValue().equals("y")){
					System.out.println(row.getCell(row.getLastCellNum()-2).getStringCellValue());
					for(int j = 0 ;j<row.getLastCellNum()-2; j++){
						fields[j] = (String)(row.getCell(j).getCellType()==XSSFCell.CELL_TYPE_STRING?
								row.getCell(j).getStringCellValue():String.valueOf(Math.round(row.getCell(j).getNumericCellValue())));
					}
					
					records.add(fields);
				}
				
			}
			
				Object[][] results = new Object[records.size()][];
				
				for(int i = 0;i<records.size();i++){
					results[i]= records.get(i);
				}
				
				return results;
	}

	
	public static int getLastColumnNum(int rownum){
		return ExcelWSheet.getRow(rownum).getLastCellNum()-1;
	}
	
	//获取指定Sheet中的总行数
	public static int getRowCount(String SheetName){
			
		//System.out.println(SheetName);
			
			
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		int number = ExcelWSheet.getLastRowNum();
		//System.out.println(number);
		return number;
	}
	
	//读取指定Sheet中的指定单元格函数，此函数只支持扩展名为.xlsx的Excel文件
	public static String getCellData(String SheetName, int RowNum,int ColNum) {
			
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		try{
				String CellData=null;
				//通过函数参数指定单元格的行号和列号，获取指定的单元格对象
				Cell =ExcelWSheet.getRow(RowNum).getCell(ColNum);
				//如果单元格的内容为字符串类型，则使用getStringCellValue方法获取单元格的内容
				//如果单元格的内容为数字类型，则使用getNumericeCellValue方法获取单元格的内容
				if(Cell==null){
					return CellData;
				} else
				 {
					CellData = Cell.getCellType()==XSSFCell.CELL_TYPE_STRING?Cell.getStringCellValue()+""
						: String.valueOf(Math.round(Cell.getNumericCellValue()));
				
				//函数返回指定单元格的字符串内容
				 return CellData;
				 }
				
		} catch(Exception e){
										
				e.printStackTrace();
				//读取遇到异常，则返回空字符串
				return "";
		} 
			
	}
			
			
}
		
		
	

