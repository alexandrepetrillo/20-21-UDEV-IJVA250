package com.example.demo.service.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ClientDto;
import com.example.demo.service.ClientService;

@Service
public class ExportClientXLSXService {

	@Autowired
    private ClientService clientService;
	
    public void exportAll(OutputStream outputStream) throws IOException {
        List<ClientDto> clients = clientService.findAllClients();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Clients");
        
        int row = 0;
        Row headerRow = sheet.createRow(row);
        Cell cellNom = headerRow.createCell(0);
        cellNom.setCellValue("Nom");
        Cell cellPrenom = headerRow.createCell(1);
        cellPrenom.setCellValue("Prénom");
        Cell cellAge = headerRow.createCell(2);
        cellAge.setCellValue("Âge");
        
        for (ClientDto client : clients) {
        	row++;
        	Row clientRow = sheet.createRow(row);
        	Cell clientNom = clientRow.createCell(0);
        	clientNom.setCellValue(client.nom);
        	Cell clientPrenom = clientRow.createCell(1);
        	clientPrenom.setCellValue(client.prenom);
        	Cell clientAge = clientRow.createCell(2);
        	clientAge.setCellValue(client.getAge()+ " ans");
        }

        workbook.write(outputStream);
        workbook.close();
    }
}
