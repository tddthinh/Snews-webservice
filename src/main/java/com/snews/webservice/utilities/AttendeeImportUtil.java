package com.snews.webservice.utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.snews.webservice.entities.Attendee;
import com.snews.webservice.entities.LabelInfo;

public class AttendeeImportUtil {
	public static void readWorkbook(File file, ArrayList<Attendee> attendees, HashMap<Integer, LabelInfo> mapping)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		Workbook workbook = WorkbookFactory.create(file);
		Sheet sheet = workbook.getSheetAt(0);
		ArrayList<String> columnNames = new ArrayList<>();
		DataFormatter dataFormatter = new DataFormatter();
		sheet.forEach(row -> {
			if (row.getRowNum() == 0) {
				row.forEach(cell -> {
					String columnName = dataFormatter.formatCellValue(cell);
					columnNames.add(columnName);
				});
			} else {
				String code = dataFormatter.formatCellValue(row.getCell(0));
				String fullname = dataFormatter.formatCellValue(row.getCell(1));
				String email = dataFormatter.formatCellValue(row.getCell(2));
				String rfid = dataFormatter.formatCellValue(row.getCell(3));
				Attendee att = new Attendee(code, fullname, email, rfid);
				attendees.add(att);
				int additionInfoIndexStart = 4;
				ArrayList<LabelInfo> labels = new ArrayList<>();
				row.forEach(cell -> {
					int currentIndex = cell.getColumnIndex();
					if (currentIndex >= additionInfoIndexStart) {
						String prefix = columnNames.get(currentIndex);
						String info = dataFormatter.formatCellValue(cell);
						String content = prefix + " " + info;
						Integer key = content.hashCode();
						if (!mapping.containsKey(key)) {
							mapping.put(content.hashCode(), new LabelInfo(content));
						}
						labels.add(mapping.get(key));
					}
				});
				att.setLabelInfos(labels);
			}
		});
		workbook.close();
	}
}
