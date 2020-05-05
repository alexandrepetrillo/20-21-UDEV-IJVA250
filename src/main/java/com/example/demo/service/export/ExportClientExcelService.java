package com.example.demo.service.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ClientDto;
import com.example.demo.service.ClientService;

@Service
public class ExportClientExcelService {

	@Autowired
	private ClientService clientService;

	public void exportAll(OutputStream outputStream) throws IOException {

		List<ClientDto> data = clientService.findAllClients();
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("clients");

		Row headerRow = sheet.createRow(0);
		CellStyle style = createBorderedStyle(workbook);
		headerRow.setRowStyle(style);
		Cell cellnom = headerRow.createCell(0);
		Cell cellprenom = headerRow.createCell(1);
		Cell cellage = headerRow.createCell(2);
		cellnom.setCellStyle(style);
		cellnom.setCellValue("Nom");
		cellprenom.setCellValue("Prénom");
		cellage.setCellValue("Age");

		for (int i = 1; i <= data.size(); i++) {
			Row row = sheet.createRow(i);
			Cell cellNomClient = row.createCell(0);
			Cell cellPrenomClient = row.createCell(1);
			Cell cellAgeClinet = row.createCell(2);
			ClientDto client = data.get(i - 1);
			cellNomClient.setCellValue(client.getNom());
			cellPrenomClient.setCellValue(client.getPrenom());
			cellAgeClinet.setCellValue(client.getAge());
		}

		Integer rows = sheet.getLastRowNum();
		for (int y = 0; y < rows; y++) {
			int cellesnu = sheet.getRow(y).getLastCellNum();
			for (int k = 0; k < cellesnu; k++) {
				Cell celle = sheet.getRow(y).getCell(k);
				celle.setCellStyle(style);
			}

		}
		workbook.write(outputStream);
		workbook.close();

	}

	private static CellStyle createBorderedStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		style.setBorderRight(BorderStyle.THICK);
		style.setRightBorderColor(IndexedColors.BLUE.getIndex());
		style.setBorderBottom(BorderStyle.THICK);
		style.setBottomBorderColor(IndexedColors.BLUE.getIndex());
		style.setBorderLeft(BorderStyle.THICK);
		style.setLeftBorderColor(IndexedColors.BLUE.getIndex());
		style.setBorderTop(BorderStyle.THICK);
		style.setTopBorderColor(IndexedColors.BLUE.getIndex());
		return style;
	}

}
