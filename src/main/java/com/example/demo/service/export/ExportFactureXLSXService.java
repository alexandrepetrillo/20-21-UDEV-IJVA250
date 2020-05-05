package com.example.demo.service.export;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.ClientDto;
import com.example.demo.dto.FactureDto;
import com.example.demo.dto.LigneFactureDto;
import com.example.demo.service.ClientService;
import com.example.demo.service.FactureService;

import autre.Facture;

@Service
public class ExportFactureXLSXService {
	
	@Autowired
    private ClientService clientService;
	
	@Autowired
	private FactureService factureService;
	
	private Workbook workbook = new XSSFWorkbook();
	
    public void exportAll(OutputStream outputStream) throws IOException {
    	List<FactureDto> facturesAll = factureService.findAllFactures();
        List<ClientDto> clients = clientService.findAllClients();

        for (FactureDto factureDto : facturesAll) {
        	
        	createSheetClient(factureDto.client); 
        	createSheetFacture(factureDto);
        }

        workbook.write(outputStream);
        workbook.close();
    }
    
    private void createSheetClient(ClientDto client) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    	// créer une nouvelle feuille client que si elle n'existe pas
    	if (workbook.getSheetIndex(client.nom + " " + client.prenom) == -1) {
    		Sheet sheet = workbook.createSheet(client.nom + " " + client.prenom);
            
            Row rowNom = sheet.createRow(0);
            Cell headerNom = rowNom.createCell(0);
            headerNom.setCellValue("Nom");
            Cell cellNom = rowNom.createCell(1);
            cellNom.setCellValue(client.nom);
            
            Row rowPrenom = sheet.createRow(1);
            Cell headerPrenom = rowPrenom.createCell(0);
            headerPrenom.setCellValue("Prénom");
            Cell cellPrenom = rowPrenom.createCell(1);
            cellPrenom.setCellValue(client.prenom);
            
            Row rowDate = sheet.createRow(2);
            Cell headerDate = rowDate.createCell(0);
            headerDate.setCellValue("Date de naissance");
            Cell cellDate = rowDate.createCell(1);
            cellDate.setCellValue(client.dateNaissance.format(dateFormatter));
    	}
    }
    
    private void createSheetFacture(FactureDto facture) {
    	Sheet sheet = workbook.createSheet("Facture n° " + facture.id);
    	int row = 0;
    	
    	Row headerRow = sheet.createRow(0);
        Cell headerDesignation = headerRow.createCell(0);
        headerDesignation.setCellValue("Désignation");
        Cell headerQte = headerRow.createCell(1);
        headerQte.setCellValue("Quantité");
        Cell headerPU = headerRow.createCell(2);
        headerPU.setCellValue("Prix unitaire");
        
        for (LigneFactureDto lf : facture.ligneFactures) {
        	ArticleDto article = lf.article;
        	row++;
        	
        	Row rowArticle = sheet.createRow(row);
            Cell cellDesignation = rowArticle.createCell(0);
            cellDesignation.setCellValue(article.libelle);
            Cell cellQte = rowArticle.createCell(1);
            cellQte.setCellValue(lf.quantite);
            Cell cellPU = rowArticle.createCell(2);
            cellPU.setCellValue(article.prix + " €");
        }
    }

}
