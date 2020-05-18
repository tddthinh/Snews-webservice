
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.snews.webservice.entities.Attendee;
import com.snews.webservice.entities.LabelInfo;
import com.snews.webservice.utilities.AttendeeImportUtil;

public class testExcelReading {
	public static final String SAMPLE_XLSX_FILE_PATH = "D:\\Users\\THINH\\Documents\\Mon Hoc\\LuanVan\\bao cao\\danhsach.xlsx";

/*	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException{
		// Creating a Workbook from an Excel file (.xls or .xlsx)
		File file = new File(SAMPLE_XLSX_FILE_PATH);
		ArrayList<Attendee> attendees = new ArrayList<>();
		HashMap<Integer, LabelInfo> mapping = new HashMap<>();
		AttendeeImportUtil.readWorkbook(file, attendees, mapping);
		
		
		
		attendees.forEach(a -> System.out.println(a.toString()));
		mapping.forEach((k,v) ->{
			LabelInfo lb = v;
			lb.setLbContent("xxx");
		});
		attendees.forEach(a -> System.out.println(a.toString()));
		
	}*/
}
