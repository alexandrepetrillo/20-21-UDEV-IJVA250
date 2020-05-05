package com.example.demo.service.export;

import com.example.demo.dto.ClientDto;
import com.example.demo.dto.FactureDto;
import com.example.demo.dto.LigneFactureDto;
import com.example.demo.service.ClientService;
import com.example.demo.service.FactureService;
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
public class ExportFactureExcelService {

    @Autowired
    ClientService clientService;

    @Autowired
    FactureService factureService;

    public void exportAll(OutputStream outputStream) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet;

        List<ClientDto> listeClients = clientService.findAllClients();
        List<FactureDto> listeFactures = factureService.findAllFactures();

        for(ClientDto client : listeClients) {

            //Feuille client
            sheet = workbook.createSheet(client.nom+" "+client.prenom);
            Row headerRow = sheet.createRow(0);
            Cell cellTitleNom = headerRow.createCell(0);
            cellTitleNom.setCellValue("Nom");
            Cell cellNom = headerRow.createCell(1);
            cellNom.setCellValue(client.nom);
            headerRow = sheet.createRow(1);
            Cell cellTitlePrenom = headerRow.createCell(0);
            cellTitlePrenom.setCellValue("Prenom");
            Cell cellPrenom = headerRow.createCell(1);
            cellPrenom.setCellValue(client.prenom);
            headerRow = sheet.createRow(2);
            Cell cellTitleDateNaissance = headerRow.createCell(0);
            cellTitleDateNaissance.setCellValue("Date de naissance");
            Cell cellDateDeNaissance = headerRow.createCell(1);
            cellDateDeNaissance.setCellValue(client.dateNaissance.toString());
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);

            //Feuille facture avec détails
            for(FactureDto facture : listeFactures) {
                if(client.hasChanged(facture.client)) {
                    sheet = workbook.createSheet("Facture n°"+facture.id);
                    headerRow = sheet.createRow(0);
                    Cell cellTitleDesignation = headerRow.createCell(0);
                    cellTitleDesignation.setCellValue("Désignation");
                    Cell cellTitleQuantite = headerRow.createCell(1);
                    cellTitleQuantite.setCellValue("Quantité");
                    Cell cellTitlePrix = headerRow.createCell(2);
                    cellTitlePrix.setCellValue("Prix unitaire");

                    int i=1;
                    for(LigneFactureDto lignes:facture.ligneFactures) {
                        headerRow = sheet.createRow(i);
                        Cell cellDesignation = headerRow.createCell(0);
                        cellDesignation.setCellValue(lignes.article.libelle);
                        Cell cellQuantite = headerRow.createCell(1);
                        cellQuantite.setCellValue(lignes.quantite);
                        Cell cellPrix = headerRow.createCell(2);
                        cellPrix.setCellValue(lignes.article.prix);
                        i++;
                    }
                    sheet.autoSizeColumn(0);
                    sheet.autoSizeColumn(2);
                }
            }
        }
        workbook.write(outputStream);
        workbook.close();
    }
}


