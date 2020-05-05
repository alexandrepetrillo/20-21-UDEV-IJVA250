package com.example.demo.service.export;

import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class ExportClientExcelService {

    @Autowired
    private ClientRepository clientRepository;

    public void exportAll(OutputStream outputStream) throws IOException {
        List<Client> allClients = clientRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Clients");

        String value = "";
        for (int i = 0; i <= allClients.size(); i++) {
            int indexClient = i;
            if ( i > 0 ) {
                indexClient = i -1;
            }
            Client client = allClients.get(indexClient);
            Row row = sheet.createRow(i);

            // boucle colonnes
            for (int j = 0; j < 3; j++) {
                Cell cell = row.createCell(j);

                if (j == 0 && i ==0) {
                    value = "Nom";
                } else if (i == 0 && j == 1) {
                    value = "Prénom";
                } else if (j == 0) {
                    value = client.getNom();
                } else if (j == 1) {
                    value = client.getPrenom();
                } else if (j == 2) {
                    value = "ici les factures";
                }

                cell.setCellValue(value);
                sheet.autoSizeColumn(j);
            }
        }
        workbook.write(outputStream);
        workbook.close();
    }
}
