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
			//ʵ����Excel�ļ���FileInputStream����
			ExcelFile = new FileInputStream(Path);
			
			//ʵ����Excel�ļ��е�XSSFWorkbook����
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			/*
			 * ʵ����XSSFSheet����ָ��Excel�ļ��е�Sheet���ƣ���������Sheet���С��к͵�Ԫ��Ĳ���
			 */
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
		} catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	//��ȡExcel�ļ�ָ����Ԫ��ĺ���
	public static String getCellData(int RowNum, int ColNum) throws Exception{
		try{
			//ͨ����������ָ����Ԫ����кź��кţ���ȡָ���ĵ�Ԫ�����
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			/*
			 * �����Ԫ�������Ϊ�ַ������ͣ���ʹ��getStringCellValue������ȡ��Ԫ�������
			 * �����Ԫ�������Ϊ�������ͣ���ʹ��getNumericeCellValue()������ȡ��Ԫ�������
			 * ע��getNumericeCellValue��������ֵΪdouble���ͣ�����ת���ַ�������
			 * ��Cell.getNumericeCellValue()ǰ�����ӡ���,����ǿ��ת��double���͵�
			 * String����,���ӡ��� ����׳�double�����޷�ת����String���͵��쳣 :String.valueOf(Math.round(Cell.getNumericCellValue()
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
		
	
	//��Excel��ָ��Sheet�У���ȡ��һ�ΰ���ָ����������������ֵ��к�
		public static int getFirstRowContainsTestCaseID(String sheetName,String testCaseName,int colNum){
			
			int i;
			try {
				ExcelWSheet = ExcelWBook.getSheet(sheetName);
				int rowCount = ExcelUtil.getRowCount(sheetName);
				for(i=0;i<rowCount;i++){
					//ʹ��ѭ���ķ�������������������е������У��ж��Ƿ����ĳ������������Źؼ���
					if(ExcelUtil.getCellData(sheetName,i,colNum).equalsIgnoreCase(testCaseName)){
						//������������˳�forѭ���������ذ�������������Źؼ��ֵ��к�
						break;
					}
				}
				
				return i;
			} catch (Exception e) {
								
				e.printStackTrace();
				//��ȡ�����쳣���򷵻ؿ��ַ���
				return 0;
			}
		}
		
		//��ȡָ��Sheet��ĳ��������������ĸ���
		public static int getTestCaseLastStepRow(String SheetName,String testCaseName,int testCaseStartRowNumber) {
			int number=testCaseStartRowNumber;
			try {
				ExcelWSheet = ExcelWBook.getSheet(SheetName);
				/*
				 * �Ӱ���ָ������������ŵĵ�һ�п�ʼ���б�����ֱ��ĳһ�в�����ָ�������������
				 * ��ʱ�ı������Ծ��Ǵ˲�����������ĸ���
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
				//��ȡ�����쳣���򷵻ؿ��ַ���
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
			
			//��������ʼ���к�
			int startRowCount=getFirstRowContainsTestCaseID(sheetName, testCaseName, 1);
			System.out.println("startRowCount="+startRowCount);
			//�������������к�
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
	
	//��ȡָ��Sheet�е�������
	public static int getRowCount(String SheetName){
			
		//System.out.println(SheetName);
			
			
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		int number = ExcelWSheet.getLastRowNum();
		//System.out.println(number);
		return number;
	}
	
	//��ȡָ��Sheet�е�ָ����Ԫ�������˺���ֻ֧����չ��Ϊ.xlsx��Excel�ļ�
	public static String getCellData(String SheetName, int RowNum,int ColNum) {
			
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		try{
				String CellData=null;
				//ͨ����������ָ����Ԫ����кź��кţ���ȡָ���ĵ�Ԫ�����
				Cell =ExcelWSheet.getRow(RowNum).getCell(ColNum);
				//�����Ԫ�������Ϊ�ַ������ͣ���ʹ��getStringCellValue������ȡ��Ԫ�������
				//�����Ԫ�������Ϊ�������ͣ���ʹ��getNumericeCellValue������ȡ��Ԫ�������
				if(Cell==null){
					return CellData;
				} else
				 {
					CellData = Cell.getCellType()==XSSFCell.CELL_TYPE_STRING?Cell.getStringCellValue()+""
						: String.valueOf(Math.round(Cell.getNumericCellValue()));
				
				//��������ָ����Ԫ����ַ�������
				 return CellData;
				 }
				
		} catch(Exception e){
										
				e.printStackTrace();
				//��ȡ�����쳣���򷵻ؿ��ַ���
				return "";
		} 
			
	}
			
			
}
		
		
	

