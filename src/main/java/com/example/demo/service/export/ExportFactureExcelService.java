package com.example.demo.service.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.FactureRepository;

@Service
public class ExportFactureExcelService {
	
	@Autowired
	private FactureRepository factureRepository;
	
	
	@Autowired
	private ClientRepository clientRepository;
	
	
	public void exportAll(OutputStream outputStream , Long clientId) throws IOException {
		Workbook workbook = new XSSFWorkbook();
        
		Optional<Client> client = clientRepository.findById(clientId);
		Client theClient = client.get();
		
		Sheet clientDataSheet = workbook.createSheet(theClient.getPrenom() + " " + theClient.getNom());
		
		
		
		List<Facture> allFactures = factureRepository.findAll();
		
		
		for (Facture facture : allFactures) {
			if (facture.getClient().getId() == clientId) {
				
				int factureNumber = facture.getId().intValue();
				Sheet factureSheet = workbook.createSheet("Facture n° "+ factureNumber);
			}
		}
		
		
        
        
        
        workbook.write(outputStream);
        workbook.close();
	}

}
