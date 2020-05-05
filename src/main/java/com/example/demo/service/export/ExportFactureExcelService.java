package com.example.demo.service.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.FactureRepository;

@Service
public class ExportFactureExcelService {

	@Autowired
	FactureRepository factureRepository;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	ArticleRepository articleRepository;

	@Autowired
	ExportClientExcelService exportClientXLSXService;

	/**
	 * @param fileOutputStream
	 * @throws IOException
	 */
	public void exportAll(OutputStream fileOutputStream) throws IOException {

		List<Client> allClients = clientRepository.findAll();
		List<Facture> allBills = factureRepository.findAll();
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet;
		for (Client client : allClients) {

			sheet = workbook.createSheet(client.getNom() + " " + client.getPrenom());
			Row headerRow = sheet.createRow(0);
			Cell cellLastNameLib = headerRow.createCell(0);
			cellLastNameLib.setCellValue("Nom");
			Cell cellLastNameValue = headerRow.createCell(1);
			cellLastNameValue.setCellValue(client.getNom());
			headerRow = sheet.createRow(1);
			Cell cellFirstNameLib = headerRow.createCell(0);
			cellFirstNameLib.setCellValue("Prénom");
			Cell cellFirstNameValue = headerRow.createCell(1);
			cellFirstNameValue.setCellValue(client.getPrenom());
			headerRow = sheet.createRow(2);
			Cell cellBirthdayLib = headerRow.createCell(0);
			cellBirthdayLib.setCellValue("Date de naissance");
			Cell cellBirthdayValue = headerRow.createCell(1);
			cellBirthdayValue.setCellValue(client.getDateNaissance().toString());
			
			// Ajustement de la largeur des colonnes pour les onglets "fiches client"
			for (int l = 0; l < 5; l++) {
				sheet.autoSizeColumn(l);
			}

			for (Facture bill : allBills ) {
				if (client.equals(bill.getClient())) {
					sheet = workbook.createSheet("Facture n°" + bill.getId());
					headerRow = sheet.createRow(0);
					Cell cellDesignationLib = headerRow.createCell(0);
					cellDesignationLib.setCellValue("Désignation");
					formatFontForHeader(workbook, headerRow.getCell(0));
					Cell cellQuantityLib = headerRow.createCell(1);
					cellQuantityLib.setCellValue("Quantité");
					formatFontForHeader(workbook, headerRow.getCell(1));
					Cell cellPriceLib = headerRow.createCell(2);
					cellPriceLib.setCellValue("Prix unitaire");
					formatFontForHeader(workbook, headerRow.getCell(2));
					int i = 1;
					for (LigneFacture line : bill.getLigneFactures()) {
						headerRow = sheet.createRow(i);
						Cell cellDesignationValue = headerRow.createCell(0);
						cellDesignationValue.setCellValue(line.getArticle().getLibelle());
						Cell cellQuantityValue = headerRow.createCell(1);
						cellQuantityValue.setCellValue(line.getQuantite());
						Cell cellPriceValue = headerRow.createCell(2);
						cellPriceValue.setCellValue(line.getArticle().getPrix());
						i++;
					}
					
					// Ajustement de la largeur des colonnes pour les onglets "facture"
					for (int l = 0; l < 5; l++) {
						sheet.autoSizeColumn(l);
					}
				}
			}
		}
		workbook.write(fileOutputStream);
		workbook.close();
	}

	public void exportById(OutputStream fileOutputStream, Long id) throws IOException {
		List<Facture> allBills = factureRepository.findAll();
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet;
		Row headerRow;
		for (Facture bill : allBills) {
			if (bill.getClient().getId() == id) {
				sheet = workbook.createSheet("Facture n°" + bill.getId());
				headerRow = sheet.createRow(0);
				Cell cellDesignationLib = headerRow.createCell(0);
				cellDesignationLib.setCellValue("Désignation");
				Cell cellQuantityLib = headerRow.createCell(1);
				cellQuantityLib.setCellValue("Quantité");
				Cell cellPriceLib = headerRow.createCell(2);
				cellPriceLib.setCellValue("Prix unitaire");
				int i = 1;
				for (LigneFacture line : bill.getLigneFactures()) {
					headerRow = sheet.createRow(i);
					Cell cellDesignationValue = headerRow.createCell(0);
					cellDesignationValue.setCellValue(line.getArticle().getLibelle());
					Cell cellQuantityValue = headerRow.createCell(1);
					cellQuantityValue.setCellValue(line.getQuantite());
					Cell cellPriceValue = headerRow.createCell(2);
					cellPriceValue.setCellValue(line.getArticle().getPrix());
					i++;
				}
			}
		}
		workbook.write(fileOutputStream);
		workbook.close();
	}
	
	/*
	 * Méthode pour appliquer un style spécifique au header des onglets "facture"
	 */
	private static void formatFontForHeader(Workbook workbook, Cell cell) {
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Helvetica Neue");
		font.setBold(true);
		font.setFontHeightInPoints((short) 12);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		cell.setCellStyle(style);
	}

}
