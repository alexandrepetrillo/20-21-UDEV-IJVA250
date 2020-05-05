package com.example.demo.service.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ClientDto;
import com.example.demo.dto.FactureDto;
import com.example.demo.dto.LigneFactureDto;
import com.example.demo.service.ClientService;
import com.example.demo.service.FactureService;

@Service
public class ExportFactureExcelService {

	@Autowired
	private FactureService factureService;

	@Autowired
	private ClientService clientService;

	public void exportAll(OutputStream outputStream) throws IOException {

		List<FactureDto> data = factureService.findAllFactures();
		List<ClientDto> clients = clientService.findAllClients();
		Workbook workbook = new XSSFWorkbook();

		for (ClientDto client : clients) {
			Sheet sheet = workbook.createSheet(client.getNom() + " " + client.getPrenom());
			Row RowName = sheet.createRow(0);
			Row RowPrenom = sheet.createRow(1);
			Row RowDateNaissance = sheet.createRow(2);
			Cell cellTitle = RowName.createCell(0);
			Cell cellValue = RowName.createCell(1);
			cellTitle.setCellValue("Nom");
			cellValue.setCellValue(client.getNom());
			Cell cellTitlePrenom = RowPrenom.createCell(0);
			Cell cellValuePrenom = RowPrenom.createCell(1);
			cellTitlePrenom.setCellValue("Prénom");
			cellValuePrenom.setCellValue(client.getPrenom());
			Cell cellTitleAge = RowDateNaissance.createCell(0);
			Cell cellValueAge = RowDateNaissance.createCell(1);
			cellTitleAge.setCellValue("Age");
			if (client.getDateNaissance() != null) {
				cellValueAge.setCellValue(client.getDateNaissance().toString());
			} else {
				cellValueAge.setCellValue("");
			}

			for (FactureDto facture : data) {

				if (client.getId().equals(facture.getClient().getId())) {
					Sheet sheetFacture = workbook.createSheet("Facture n°" + facture.getId().toString());
					List<LigneFactureDto> ligneFacture = facture.getLigneFactures();
					Row rowTitle = sheetFacture.createRow(0);
					Cell cellDesig = rowTitle.createCell(0);
					Cell cellQuant = rowTitle.createCell(1);
					Cell cellPrix = rowTitle.createCell(2);
					cellDesig.setCellValue("Désignation");
					cellQuant.setCellValue("Quantité");
					cellPrix.setCellValue("Prix unitaire");

					Font headerFont = workbook.createFont();
					headerFont.setBold(true);

//					((CellStyle) cellDesig).setAlignment(HorizontalAlignment.CENTER);
//					((CellStyle) cellQuant).setAlignment(HorizontalAlignment.CENTER);
//					((CellStyle) cellPrix).setAlignment(HorizontalAlignment.CENTER);
					for (int i = 1; i <= ligneFacture.size(); i++) {
						LigneFactureDto ligne = ligneFacture.get(i - 1);
						Row rowLigne = sheetFacture.createRow(i);
						Cell cellValueDesig = rowLigne.createCell(0);
						Cell cellValueQuant = rowLigne.createCell(1);
						Cell cellValuePrix = rowLigne.createCell(2);
						cellValueDesig.setCellValue(ligne.getArticle().getLibelle());
						cellValueQuant.setCellValue(ligne.getQuantite());
						cellValuePrix.setCellValue(ligne.getArticle().getPrix());
					}

					for (int j = 0; j < 3; j++) {
						sheetFacture.autoSizeColumn(j);
					}
				}

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
