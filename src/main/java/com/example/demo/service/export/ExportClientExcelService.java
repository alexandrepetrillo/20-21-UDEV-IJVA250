package com.example.demo.service.export;

import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class ExportClientExcelService {

    @Autowired
    private ClientRepository clientRepository;

    /**
     * Méthode qui exporte les clients au format Excel
     * 
     * @param outputStream : le flux dans lequel on écrit
     * @throws IOException : l'exception levée en cas d'erreur
     */
    public void exportAll(OutputStream outputStream) throws IOException {
        List<Client> allClients = clientRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Feuil1");
        
        // Style pour l'en-tête du tableau
        CellStyle styleTitre = workbook.createCellStyle();
        Font fontTitre = workbook.createFont();
        fontTitre.setColor(HSSFColor.HSSFColorPredefined.PINK.getIndex());
        fontTitre.setFontName("Helvetica");
        fontTitre.setFontHeightInPoints((short)10);
        fontTitre.setBold(true);
        styleTitre.setFont(fontTitre);
        styleTitre.setVerticalAlignment(VerticalAlignment.TOP);
        styleTitre.setBorderTop(BorderStyle.MEDIUM);
        styleTitre.setBorderBottom(BorderStyle.MEDIUM);
        styleTitre.setBorderLeft(BorderStyle.MEDIUM);
        styleTitre.setBorderRight(BorderStyle.MEDIUM);
        styleTitre.setBottomBorderColor(IndexedColors.BLUE.getIndex());
        styleTitre.setTopBorderColor(IndexedColors.BLUE.getIndex());
        styleTitre.setLeftBorderColor(IndexedColors.BLUE.getIndex());
        styleTitre.setRightBorderColor(IndexedColors.BLUE.getIndex());
        
        // Style pour chaque cellule intérieure du tableau
        CellStyle styleCellule = workbook.createCellStyle();
        Font fontCellule = workbook.createFont();
        fontCellule.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        fontCellule.setFontName("Calibri");
        fontCellule.setFontHeightInPoints((short)11);
        fontCellule.setBold(false);
        styleCellule.setFont(fontCellule);
        styleCellule.setVerticalAlignment(VerticalAlignment.TOP);
        styleCellule.setBorderTop(BorderStyle.MEDIUM);
        styleCellule.setBorderBottom(BorderStyle.MEDIUM);
        styleCellule.setBorderLeft(BorderStyle.MEDIUM);
        styleCellule.setBorderRight(BorderStyle.MEDIUM);
        styleCellule.setBottomBorderColor(IndexedColors.BLUE.getIndex());
        styleCellule.setTopBorderColor(IndexedColors.BLUE.getIndex());
        styleCellule.setLeftBorderColor(IndexedColors.BLUE.getIndex());
        styleCellule.setRightBorderColor(IndexedColors.BLUE.getIndex());
        
        // On écrit l'en-tête
        Row headerRowTitres = sheet.createRow(0);
        int twipsPerInch =  1440;
        headerRowTitres.setHeight((short)(twipsPerInch*2.3/10)); // hauteur = 0.58 cm
        
        for (int i = 0; i < 3; i++) {
        	String texteCelluleTitre = "";
        	if (i == 0) {
        		texteCelluleTitre = "Nom";
        	}
        	if (i == 1) {
        		texteCelluleTitre = "Prénom";
        	}
        	if (i == 2) {
        		texteCelluleTitre = "Age";
        	}
        	Cell cellTitre = headerRowTitres.createCell(i);
        	cellTitre.setCellValue(texteCelluleTitre);
        	cellTitre.setCellStyle(styleTitre);
        }

        // On écrit les lignes intérieures
        int ligne = 1;
        for (Client client : allClients) {
        	
        	Row insideRow = sheet.createRow(ligne);
        	insideRow.setHeight((short)(twipsPerInch*2.3/10)); // hauteur = 0.58 cm
        	
        	for (int i = 0; i < 3; i++) {
        		
        		String texteCellule = "";
            	if (i == 0) {
            		texteCellule = client.getNom();
            	}
            	if (i == 1) {
            		texteCellule = client.getPrenom();
            	}
            	if (i == 2) {
            		Period difference = Period.between(client.getDateNaissance(), LocalDate.now());
            		Integer ageClient = difference.getYears();
            		if (client.getDateNaissance().getMonthValue() > LocalDate.now().getMonthValue()) {
            			ageClient = ageClient + 1;
            		} else {
            			ageClient = ageClient - 1;
            		}
    				texteCellule = ageClient + " ans";
            	}
        		Cell cell = insideRow.createCell(i);
                cell.setCellValue(texteCellule);
                cell.setCellStyle(styleCellule);
        	}
        	
        	ligne++;
        	
        }
        
        // On élargit les colonnes automatiquement pour afficher tout leur contenu
        for (int i = 0; i < 3; i++) {
        	sheet.autoSizeColumn(i);
        }
        
        // Pour obtenir les mêmes largeurs de colonnes que le fichier attendu
        // il a fallu redéfinir manuellement les largeurs des 3 colonnes
        sheet.setColumnWidth(0, 1792); // largeur = 1.37 cm
        sheet.setColumnWidth(1, 3840); // largeur = 2.94 cm
        sheet.setColumnWidth(2, 2338); // largeur = 1.79 cm

        workbook.write(outputStream);
        workbook.close();
    }

}
