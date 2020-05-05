package com.example.demo.service.export;

import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ClientDto;
import com.example.demo.dto.FactureDto;
import com.example.demo.service.ClientService;
import com.example.demo.service.FactureService;

@Service
public class ExportFacturesXlsx {

	@Autowired
	private FactureService factureService;
	
	@Autowired
	private ClientService clientService;
	
	public void exportAll(OutputStream outputStream) throws IOException{
		List<FactureDto> allFactures = factureService.findAllFactures();
		
		List<ClientDto> allClients = clientService.findAllClients();
		
		Workbook workbook = new XSSFWorkbook();
		
		Sheet sheet;
		
		for(ClientDto client : allClients) {
			sheet = workbook.createSheet(client.nom + " " + client.prenom);
			
			Row headerRow = sheet.createRow(0);
			Cell cell = headerRow.createCell(0);
			cell.setCellValue("Nom");
			cell = headerRow.createCell(1);
			cell.setCellValue(client.nom);
			
			headerRow = sheet.createRow(1);
			cell = headerRow.createCell(0);
			cell.setCellValue("Prénom");
			cell = headerRow.createCell(1);
			cell.setCellValue(client.prenom);
			
			headerRow = sheet.createRow(2);
			cell = headerRow.createCell(0);
			cell.setCellValue("Date de naissance");
			cell = headerRow.createCell(1);
			cell.setCellValue(client.dateNaissance.format(DateTimeFormatter.ofPattern("dd/MM/yy")).toString());
			
			for(FactureDto facture : allFactures) {
				
				List<FactureDto> facturesClient = factureService.findAllFactures();
				
				for(FactureDto factureClient : facturesClient) {
					ClientDto clientAssocie = factureClient.client;
					
					factureService.
					
				}
				
				
				sheet = workbook.createSheet("Facture n°" + factureClient.id);
			}
			
		}
		
		workbook.write(outputStream); //Permet d'écrire dans un fichier qui ne prend pas du texte (xlsx,pdf etc) prennent du byte
		workbook.close();
	}
	
}
