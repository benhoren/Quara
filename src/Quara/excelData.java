package Quara;

import org.apache.poi.xssf.usermodel.XSSFSheet;

public interface excelData {

	public String[] toArray();
	public void toSheet(XSSFSheet sheet);
	
}
