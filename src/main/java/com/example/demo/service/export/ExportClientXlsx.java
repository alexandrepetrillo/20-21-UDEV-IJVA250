package com.example.demo.service.export;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ClientDto;
import com.example.demo.service.ClientService;

@Service
public class ExportClientXlsx {

	@Autowired
	private ClientService clientService;
	
	public void exportAll(OutputStream outputStream) throws IOException {
		List<ClientDto> allClients = clientService.findAllClients();
		
		Workbook workbook = new XSSFWorkbook();
		
		XSSFFont font = ((XSSFWorkbook) workbook).createFont();
		XSSFCellStyle border =  (XSSFCellStyle) workbook.createCellStyle();
		XSSFColor color = new XSSFColor(new java.awt.Color(0, 0, 255));
		
		XSSFCellStyle headerStyle = (XSSFCellStyle) workbook.createCellStyle();
					
		headerStyle.setBorderTop(BorderStyle.MEDIUM);
		headerStyle.setBorderBottom(BorderStyle.MEDIUM);
		headerStyle.setBorderLeft(BorderStyle.MEDIUM);
		headerStyle.setBorderRight(BorderStyle.MEDIUM);
		headerStyle.setTopBorderColor(color);
		headerStyle.setRightBorderColor(color);
		headerStyle.setBottomBorderColor(color);
		headerStyle.setLeftBorderColor(color);	
			
		border.setBorderTop(BorderStyle.MEDIUM);
		border.setBorderBottom(BorderStyle.MEDIUM);
		border.setBorderLeft(BorderStyle.MEDIUM);
		border.setBorderRight(BorderStyle.MEDIUM);
		border.setTopBorderColor(color);
		border.setRightBorderColor(color);
		border.setBottomBorderColor(color);
		border.setLeftBorderColor(color);		 
		
		font.setFontName("Helvetica");
		font.setFontHeightInPoints((short) 10);
		font.setColor((short) 6);
		font.setBold(true);
		
		headerStyle.setFont(font);
		
		Sheet sheet = workbook.createSheet("Clients");
	
		Row headerRow = sheet.createRow(0);
		
		Cell cell = headerRow.createCell(0);
		
		cell.setCellValue("Nom");
		cell.setCellStyle(headerStyle);

		
		cell = headerRow.createCell(1);
		
		cell.setCellValue("Prénom");
		cell.setCellStyle(headerStyle);

		
		cell = headerRow.createCell(2);
		
		cell.setCellValue("Age");
		cell.setCellStyle(headerStyle);

		
		for(int i = 0; i < allClients.size();i++) {
			
			headerRow = sheet.createRow(i+1);
			 
			cell = headerRow.createCell(0); 
			cell.setCellValue(allClients.get(i).nom);
			cell.setCellStyle(border);

			cell = headerRow.createCell(1); 
			cell.setCellValue(allClients.get(i).prenom); 
			cell.setCellStyle(border);
			 
			cell = headerRow.createCell(2); 
			cell.setCellValue(Period.between(allClients.get(i).dateNaissance, LocalDate.now()).getYears()+ " ans"); 
			cell.setCellStyle(border);
			 
			for(int j =0; j<3;j++) {
				sheet.autoSizeColumn(j) ;
			}
			 
		
		}
		
		workbook.write(outputStream); //Permet d'écrire dans un fichier qui ne prend pas du texte (xlsx,pdf etc) prennent du byte
		workbook.close();
	};
}
