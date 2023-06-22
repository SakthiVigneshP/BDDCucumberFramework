package csf.common;

import java.util.HashMap;

import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DataExtraction {
	
	public HashMap<String,String> fecthTestData(String inputFile, String testCaseName){
		
		HashMap<String,String> fieldTitleValue = new HashMap<String,String>();
		List<String> data = new ArrayList<String>();
		
		File file = null;
		FileInputStream fis = null;
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		
		try{
			file = new File(inputFile);
			fis = new FileInputStream(file);
			
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet("DataSheet");
			
			
			
			int rowCount = 1;
			String sheetTestCaseName = null;
			
			Iterator<Row> rowIterator = sheet.rowIterator();
			Iterator cells;
			while(rowIterator.hasNext()) {
				Row row = rowIterator.next();
				
				if(rowCount==1) {
					cells = row.cellIterator();
					while(cells.hasNext()) {
						XSSFCell cell = (XSSFCell) cells.next();
						data.add(cell.toString().trim());
						
						
					}
				}
				
				sheetTestCaseName = row.getCell(1).toString().trim();
				
				if(testCaseName.equalsIgnoreCase(sheetTestCaseName)) {
					
					cells = row.cellIterator();
					
					
					for(int i=0;i< data.size();i++) {
						XSSFCell cell = (XSSFCell) cells.next();
						if(!cell.toString().trim().equalsIgnoreCase("NA")||
								!cell.toString().trim().equalsIgnoreCase("")) {
							fieldTitleValue.put(data.get(i).toString().trim(), cell.toString().trim());
						}
					}
						break;
					}
				rowCount = rowCount + 1;
				}
				for(int i=0;i<data.size();i++) {
					String title = data.get(i).toString();
				}
				workbook.close();
			} catch(IOException e) {
				e.printStackTrace();
			}finally {
				if(fis!=null) {
					try {
						fis.close();
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
			return fieldTitleValue;		
	}

}
