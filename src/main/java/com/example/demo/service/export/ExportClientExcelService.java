package com.example.demo.service.export;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import com.example.demo.repository.ClientRepository;

@Service
public class ExportClientExcelService {

	@Autowired
	private ClientRepository clientRepository;

	public void exportAll(OutputStream outputStream) throws IOException {

		List<Client> allClients = clientRepository.findAll();
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Clients");

		Row row = sheet.createRow(0);

		// Style pour le header
		CellStyle headerStyle = workbook.createCellStyle();

		// Définition du style pour le texte du header
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setColor(IndexedColors.PINK.getIndex());
		headerStyle.setFont(headerFont);
		
		// Définition de la largeur des bordures
		headerStyle.setBorderBottom(BorderStyle.MEDIUM);
		headerStyle.setBorderTop(BorderStyle.MEDIUM);
		headerStyle.setBorderLeft(BorderStyle.MEDIUM);
		headerStyle.setBorderRight(BorderStyle.MEDIUM);

		// Définition de la couleur des bordures
		headerStyle.setTopBorderColor(IndexedColors.BLUE.getIndex());
		headerStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());
		headerStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex());
		headerStyle.setLeftBorderColor(IndexedColors.BLUE.getIndex());

		
		// Remplissage du header et application du style
		Row headerRow = sheet.createRow(0);
		Cell headerColLastName = headerRow.createCell(0);
		headerColLastName.setCellValue("Nom");
		headerColLastName.setCellStyle(headerStyle);
		Cell headerColFirstName = headerRow.createCell(1);
		headerColFirstName.setCellValue("Prenom");
		headerColFirstName.setCellStyle(headerStyle);
		Cell headerColAge = headerRow.createCell(2);
		headerColAge.setCellValue("Age");
		headerColAge.setCellStyle(headerStyle);

		// Style par défaut
		CellStyle defaultStyle = workbook.createCellStyle();
		
		// Remplissage des autres cellules
		for (Client client : allClients) {
			row = sheet.createRow(row.getRowNum() + 1);
			Cell colLastName = row.createCell(0);
			// colLastName.setCellStyle(defaultStyle);
			addBorders(workbook, row.getCell(0));
			colLastName.setCellValue(client.getNom());
			Cell colFirstName = row.createCell(1);
			// colFirstName.setCellStyle(defaultStyle);
			addBorders(workbook, row.getCell(1));
			colFirstName.setCellValue(client.getPrenom());
			Cell colAge = row.createCell(2);
			colAge.setCellValue(Period.between(client.getDateNaissance(), LocalDate.now()).getYears() + " ans");
			// colAge.setCellStyle(defaultStyle);
			addBorders(workbook, row.getCell(2));

		}

		// Ajustement de la largeur des colonnes en fonction du texte contenu
		for (int i = 0; i < 5; i++) {
			sheet.autoSizeColumn(i);
		}

		workbook.write(outputStream);
		workbook.close();

	}

	/*
	 * Méthode pour appliquer des bordures à une cellule
	 */
	private static void addBorders(Workbook workbook, Cell cell) {
		CellStyle style = workbook.createCellStyle();

		// Définition de la largeur des bordures
		style.setBorderBottom(BorderStyle.MEDIUM);
		style.setBorderTop(BorderStyle.MEDIUM);
		style.setBorderLeft(BorderStyle.MEDIUM);
		style.setBorderRight(BorderStyle.MEDIUM);

		// Définition de la couleur des bordures
		style.setTopBorderColor(IndexedColors.BLUE.getIndex());
		style.setRightBorderColor(IndexedColors.BLUE.getIndex());
		style.setBottomBorderColor(IndexedColors.BLUE.getIndex());
		style.setLeftBorderColor(IndexedColors.BLUE.getIndex());

		cell.setCellStyle(style);
	}

}
