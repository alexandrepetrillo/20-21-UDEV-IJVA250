package com.example.demo.service.export;

import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.FactureRepository;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Service
public class ExportFactureExcelService {

    @Autowired
    private FactureRepository factureRepository;
    
    @Autowired
    private ClientRepository clientRepository;

    /**
     * Méthode qui exporte toutes les factures en base et les exporte (regroupées par client) dans un fichier Excel
     * 
     * @param outputStream : le stream dans lequel on écrit
     * @throws IOException : l'exception levée en cas d'erreur
     */
    public void exportAll(OutputStream outputStream) throws IOException {
    	
    	Workbook workbook = new XSSFWorkbook();
    	
        // Style pour l'en-tête du tableau
        CellStyle styleTitre = workbook.createCellStyle();
        Font fontTitre = workbook.createFont();
        fontTitre.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        fontTitre.setBold(true);
        styleTitre.setFont(fontTitre);
        styleTitre.setBorderTop(BorderStyle.THIN);
        styleTitre.setBorderBottom(BorderStyle.THIN);
        styleTitre.setBorderLeft(BorderStyle.THIN);
        styleTitre.setBorderRight(BorderStyle.THIN);
        styleTitre.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styleTitre.setTopBorderColor(IndexedColors.BLACK.getIndex());
        styleTitre.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        styleTitre.setRightBorderColor(IndexedColors.BLACK.getIndex()); 
        styleTitre.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        styleTitre.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        // Style pour chaque cellule intérieure du tableau
        CellStyle styleCellule = workbook.createCellStyle();
        Font fontCellule = workbook.createFont();
        fontCellule.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        fontCellule.setBold(false);
        styleCellule.setFont(fontCellule);
        styleCellule.setBorderTop(BorderStyle.THIN);
        styleCellule.setBorderBottom(BorderStyle.THIN);
        styleCellule.setBorderLeft(BorderStyle.THIN);
        styleCellule.setBorderRight(BorderStyle.THIN);
        styleCellule.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styleCellule.setTopBorderColor(IndexedColors.BLACK.getIndex());
        styleCellule.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        styleCellule.setRightBorderColor(IndexedColors.BLACK.getIndex());
    	
        // On créé un onglet pour chaque client avec ses infos personnelles
    	List<Client> allClients = clientRepository.findAll();

        for (Client client : allClients) {
        	
        	Long idClient = client.getId();
        	
        	Sheet sheet = workbook.createSheet(client.getNom() + " " + client.getPrenom());
        	
        	for (int i = 0; i < 3; i++) {
        		
        		Row headerRowTitres = sheet.createRow(i);
        		
            	String texteCelluleTitre = "";
            	if (i == 0) {
            		texteCelluleTitre = "Nom";
            	}
            	if (i == 1) {
            		texteCelluleTitre = "Prénom";
            	}
            	if (i == 2) {
            		texteCelluleTitre = "Date de naissance";
            	}
            	Cell cellTitre = headerRowTitres.createCell(0);
            	cellTitre.setCellValue(texteCelluleTitre);
            	cellTitre.setCellStyle(styleTitre);
            	
            	String texteCelluleInfos = "";
            	if (i == 0) {
            		texteCelluleInfos = client.getNom();
            	}
            	if (i == 1) {
            		texteCelluleInfos = client.getPrenom();
            	}
            	if (i == 2) {
            		texteCelluleInfos = client.getDateNaissance().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            	}
            	Cell cellInfos = headerRowTitres.createCell(1);
            	cellInfos.setCellValue(texteCelluleInfos);
            	cellInfos.setCellStyle(styleCellule);
            }
        	
            for (int i = 0; i < 3; i++) {
            	sheet.autoSizeColumn(i);
            }
            
            // Après l'onglet de chaque client, on rajoute un onglet pour chacune de ses factures
            List<Facture> allFactures = factureRepository.findAll();
            
            for (Facture facture : allFactures) {
            	if (facture.getClient().getId() == idClient) {

            		Sheet sheetFact = workbook.createSheet("Facture n° " + facture.getId());
            		Row headerRowFactureTitres = sheetFact.createRow(0);
            			
            		for (int j = 0; j < 3; j++) {
                		
                    	String texteCelluleFactureTitre = "";
                    	if (j == 0) {
                    		texteCelluleFactureTitre = "Désignation";
                    	}
                    	if (j == 1) {
                    		texteCelluleFactureTitre = "Quantité";
                    	}
                    	if (j == 2) {
                    		texteCelluleFactureTitre = "Prix unitaire";
                    	}
                    	Cell cellFactureTitre = headerRowFactureTitres.createCell(j);
                    	cellFactureTitre.setCellValue(texteCelluleFactureTitre);
                    	cellFactureTitre.setCellStyle(styleTitre);
                    	
                    	int ligne = 1;
                    	Set<LigneFacture> allLignesFacture = facture.getLigneFactures();
                    	for (LigneFacture ligneFact : allLignesFacture) {
                    		
                    		Row insideFactRow = sheetFact.createRow(ligne);
                    		for (int k = 0; k < 3; k++) {
                    			
                            	String texteCelluleFactureInfos = "";
                            	if (k == 0) {
                            		texteCelluleFactureInfos = ligneFact.getArticle().getLibelle();
                            	}
                            	if (k == 1) {
                            		texteCelluleFactureInfos = Integer.toString(ligneFact.getQuantite());
                            	}
                            	if (k == 2) {
                            		texteCelluleFactureInfos = Double.toString(ligneFact.getArticle().getPrix());
                            	}
                            	Cell cellFactureInfos = insideFactRow.createCell(k);
                            	cellFactureInfos.setCellValue(texteCelluleFactureInfos);
                            	cellFactureInfos.setCellStyle(styleCellule);
                    		}
                    		ligne++;
                    	}

                    }
            		
                    for (int i = 0; i < 3; i++) {
                    	sheetFact.autoSizeColumn(i);
                    }
            	}
            }
        }
        workbook.write(outputStream);
        workbook.close();
    }

}
