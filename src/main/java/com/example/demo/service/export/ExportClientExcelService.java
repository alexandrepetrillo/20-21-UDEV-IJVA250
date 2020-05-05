package com.example.demo.service.export;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;

@Service
public class ExportClientExcelService {

	@Autowired
	private ClientRepository clientRepository;
	
	public void exportAll(OutputStream outputStream) throws IOException {
		List<Client> allClients = clientRepository.findAll();
		
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Clients");
		
		Row headerRow = sheet.createRow(0);
        Cell cellNom = headerRow.createCell(0);
        Cell cellPrenom = headerRow.createCell(1);
        Cell cellAge = headerRow.createCell(2);
        cellNom.setCellValue("Nom");
        cellPrenom.setCellValue("Prenom");
        cellAge.setCellValue("Age");
        
        Integer rowNumber = 1;
        
        for(Client client : allClients) {
        	Row dataRow = sheet.createRow(rowNumber);
        	int age = LocalDate.now().getYear() - client.getDateNaissance().getYear();
        	Cell cellDataNom = dataRow.createCell(0);
        	Cell cellDataPrenom = dataRow.createCell(1);
        	Cell cellDataAge = dataRow.createCell(2);
        	cellDataNom.setCellValue(client.getNom());
        	cellDataPrenom.setCellValue(client.getPrenom());
        	cellDataAge.setCellValue(age);
        	rowNumber ++;
        }
		
		workbook.write(outputStream);
        workbook.close();
	}
}
