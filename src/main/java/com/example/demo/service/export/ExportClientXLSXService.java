package com.example.demo.service.export;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor.BLUE;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder.BorderSide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ClientDto;
import com.example.demo.service.ClientService;

@Service
public class ExportClientXLSXService {

	@Autowired
	ClientService clientservice;
	
	public void exportAll(OutputStream fileOutputStream) throws IOException {
    	List<ClientDto> toutLesClient = clientservice.findAllClients();
    	// je cree manuellement les celule 0 et 1 a cause de la nature de notre DTO, 
    	Workbook workbook = new XSSFWorkbook();
    	XSSFCellStyle style=(XSSFCellStyle) workbook.createCellStyle();
    	style.setBorderColor(BorderSide.TOP, new XSSFColor(new java.awt.Color(128, 0, 128)));
    	style.setBorderColor(BorderSide.LEFT, new XSSFColor(new java.awt.Color(128, 0, 128)));
    	style.setBorderColor(BorderSide.RIGHT, new XSSFColor(new java.awt.Color(128, 0, 128)));
    	style.setBorderColor(BorderSide.BOTTOM, new XSSFColor(new java.awt.Color(128, 0, 128)));
    	
    	Sheet sheet = workbook.createSheet("Clients");
    	Row headerRow = sheet.createRow(0);
		Cell cellNomName = headerRow.createCell(0);
		cellNomName.setCellValue("Nom");
		Cell cellPrenomName = headerRow.createCell(1);
		cellPrenomName.setCellValue("Prenom");
		Cell cellAgeName = headerRow.createCell(2);
		cellAgeName.setCellValue("Age");
		// etant donnée le zero est reservé au nomage
    	int i=1;
    	for(ClientDto client:toutLesClient) {
    		headerRow = sheet.createRow(i);
    		Cell cellNom = headerRow.createCell(0);
    		cellNom.setCellValue(client.getNom());
    		Cell cellPrenom = headerRow.createCell(1);
    		cellPrenom.setCellValue(client.getPrenom());
    		Cell cellAge = headerRow.createCell(2);
    		cellAge.setCellValue(LocalDate.now().getYear()-client.getDateDeNaissance().getYear()+" ans");
        	i++;
    	}
    	
    	workbook.write(fileOutputStream);
    	workbook.close();
       }
	
	public void export(ClientDto c,OutputStream fileOutputStream) throws IOException {
    	List<ClientDto> toutLesClient = clientservice.findAllClients();
    	// je cree manuellement les celule 0 et 1 a cause de la nature de notre DTO, 
    	Workbook workbook = new XSSFWorkbook();
    	Sheet sheet = workbook.createSheet(c.getNom()+" "+c.getPrenom());
    	Row headerRow = sheet.createRow(0);
		Cell cellNomName = headerRow.createCell(0);
		cellNomName.setCellValue("Nom");
		Cell cellNomValue = headerRow.createCell(1);
		cellNomValue.setCellValue(c.getNom());
		headerRow = sheet.createRow(1);
		Cell cellPrenomName = headerRow.createCell(0);
		cellPrenomName.setCellValue("Prenom");
		Cell cellPrenomValue = headerRow.createCell(1);
		cellPrenomValue.setCellValue(c.getPrenom());
		headerRow = sheet.createRow(2);
		Cell cellDateDeNaissanceName = headerRow.createCell(0);
		cellDateDeNaissanceName.setCellValue("Date de naissance");
		Cell cellDateDeNaissanceValue = headerRow.createCell(1);
		cellDateDeNaissanceValue.setCellValue(c.getDateDeNaissance().toString());

    	workbook.write(fileOutputStream);
    	workbook.close();
       }
}
