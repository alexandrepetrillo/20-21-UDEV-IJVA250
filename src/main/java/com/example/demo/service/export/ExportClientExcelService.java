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

import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;
import com.example.demo.service.ClientService;

@Service
public class ExportClientExcelService {

	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private ClientService clientService;

	public void exportAll(OutputStream outputStream) throws IOException {
		List<Client> allClients = clientRepository.findAll();

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Clients");

		Row headerRow = sheet.createRow(0);
		Cell cell = headerRow.createCell(0);
		cell.setCellValue("Nom");

		cell = headerRow.createCell(1);
		cell.setCellValue("Prénom");

		cell = headerRow.createCell(2);
		cell.setCellValue("Age");

		int iterator = 0;
		for (Client cl : allClients) {
			iterator++;
			Row clientRow = sheet.createRow(iterator);
			// Nom client
			cell = clientRow.createCell(0);
			cell.setCellValue(cl.getNom());
			// Prénom client
			cell = clientRow.createCell(1);
			cell.setCellValue(cl.getPrenom());
			// Age client
			int age=clientService.getAgeFromLocalDateNaissance(cl);
			cell = clientRow.createCell(2);
			cell.setCellValue(age);

		}

		workbook.write(outputStream);
		workbook.close();
	}



}