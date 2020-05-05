package com.example.demo.service.export;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ClientDto;
import com.example.demo.dto.FactureDto;
import com.example.demo.dto.LigneFactureDto;
import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.FactureRepository;
import com.example.demo.service.ClientService;
import com.example.demo.service.FactureService;

@Service
public class ExportFactureExcelService {

	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private ClientService clientService;
	@Autowired
	private FactureRepository factureRepository;
	@Autowired
	private FactureService factureService;
	
	
	public void exportAll(OutputStream outputStream) throws IOException {
		List<ClientDto> allClients = clientService.findAllClients();
		List<FactureDto> allFactures = factureService.findAllFactures();
		Workbook workbook = new XSSFWorkbook();
		List<FactureDto> facts= new ArrayList<FactureDto>();
        
		String formattedDate;


		// création de la feuille de chaque client
		for (ClientDto cli : allClients) {
		Sheet sheet = workbook.createSheet(cli.getNom() + " " + cli.getPrenom());

		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue("Nom");
		cell= row.createCell(1);
		cell.setCellValue(cli.getNom());

		row= sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue("Prénom");
		cell= row.createCell(1);
		cell.setCellValue(cli.getPrenom());

		row= sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue("Date de naissance");
		cell= row.createCell(1);
		formattedDate = cli.getDateNaissance().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		cell.setCellValue(formattedDate);
		
		// facture correspondante au client
		facts = allFactures.stream().filter(x->x.getClient().getId()==cli.getId()).collect(Collectors.toList());
		for (FactureDto fact:facts) {

			sheet = workbook.createSheet("Facture N° " + fact.getId());
			
			// on créée les headers
			Row headerRow = sheet.createRow(0);
			Cell headercell = headerRow.createCell(0);
			headercell.setCellValue("Désignation");

			headercell = headerRow.createCell(1);
			headercell.setCellValue("Quantité");

			headercell = headerRow.createCell(2);
			headercell.setCellValue("Prix Unitaire");
			
			int iterator = 0;
			for (LigneFactureDto lignefa : fact.getLigneFactures()) {
				iterator++;
				Row factureRow = sheet.createRow(iterator);
				// Désignation
				cell = factureRow.createCell(0);
				cell.setCellValue(lignefa.getArticle().getLibelle());
				// Quantité
				cell = factureRow.createCell(1);
				cell.setCellValue(lignefa.getQuantite());
				// Prix Unitaire
				cell = factureRow.createCell(2);
				cell.setCellValue(lignefa.getArticle().getPrix());

			}
			
			

		}
		
		}



		workbook.write(outputStream);
		workbook.close();
	}



}