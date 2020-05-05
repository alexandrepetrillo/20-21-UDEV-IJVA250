package com.example.demo.service.export;

import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.ClientDto;
import com.example.demo.repository.ClientRepository;
import com.example.demo.service.ClientService;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class ExportClientExcelService {

	@Autowired
    private ClientService clientService;
	
	public void exportAll(OutputStream outputStream) throws IOException {

        List<ClientDto> allClients = clientService.findAllClients();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Clients");
        
        Row headerRow = sheet.createRow(0);
        
        Cell headerCellFirstname = headerRow.createCell(0);
        headerCellFirstname.setCellValue("FirstName");
        
        Cell headerCellLastname = headerRow.createCell(1);
        headerCellLastname.setCellValue("LastName");
        
        Cell headerCellAge = headerRow.createCell(2);
        headerCellAge.setCellValue("Age");
        
        int row = 1;
       
        for (ClientDto client : allClients) {
        	Row myRow = sheet.createRow(row);
        	
        	Cell cellFirstname = myRow.createCell(0);
        	cellFirstname.setCellValue(client.prenom);
        	
        	Cell cellLastname = myRow.createCell(1);
        	cellLastname.setCellValue(client.nom);
        	
        	Integer clientAge = LocalDate.now().getYear() - client.getDateNaissance().getYear();
    		
        	Cell cellAge = myRow.createCell(2);
        	cellAge.setCellValue(clientAge);
        	
        	row++;
        }


        workbook.write(outputStream);
        workbook.close();


    }

}
