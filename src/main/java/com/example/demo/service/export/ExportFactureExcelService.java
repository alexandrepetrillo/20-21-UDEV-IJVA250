package com.example.demo.service.export;

import com.example.demo.dto.LigneFactureDto;
import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.FactureRepository;
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
    private FactureRepository factureRepository;

    @Autowired
    private ClientRepository clientRepository;


    public void exportAll(OutputStream outputStream) throws IOException {
        List<Facture> allFactures = factureRepository.findAll();
        List<Client> allClients = clientRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet;

        for(Client c : allClients) {
            sheet = workbook.createSheet(c.getNom() + " " + c.getPrenom());

            Row nameRow = sheet.createRow(0);
            Cell nameCell = nameRow.createCell(0);
            nameCell.setCellValue("Nom");
            Cell clientNameCell = nameRow.createCell(1);
            clientNameCell.setCellValue(c.getNom());

            Row firstNameRow = sheet.createRow(1);
            Cell firstNameCell = firstNameRow.createCell(0);
            firstNameCell.setCellValue("Prénom");
            Cell clientFirstNameCell = firstNameRow.createCell(1);
            clientFirstNameCell.setCellValue(c.getPrenom());

            Row birthRow = sheet.createRow(2);
            Cell birthCell = birthRow.createCell(0);
            birthCell.setCellValue("Date de naissance");
            Cell clientBirthCell = birthRow.createCell(1);
            clientBirthCell.setCellValue(c.getDateNaissance().toString());

            for(Facture f : allFactures) {
                if (c.equals(f.getClient())) {
                    sheet = workbook.createSheet("Facture n°" + f.getId());
                    Row headerRow = sheet.createRow(0);
                    Cell cellLibelle = headerRow.createCell(0);
                    cellLibelle.setCellValue("Libellé");
                    Cell cellQuantity = headerRow.createCell(1);
                    cellQuantity.setCellValue("Quantité");
                    Cell cellPrice = headerRow.createCell(2);
                    cellPrice.setCellValue("Prix Unitaire");

                    int loop = 1;

                    for (LigneFacture ligne : f.getLigneFactures()) {
                        headerRow = sheet.createRow(loop);
                        Cell libelle = headerRow.createCell(0);
                        libelle.setCellValue(ligne.getArticle().getLibelle());
                        Cell quantite = headerRow.createCell(1);
                        quantite.setCellValue(ligne.getQuantite());
                        Cell prix = headerRow.createCell(2);
                        prix.setCellValue(ligne.getArticle().getPrix());

                        loop++;
                    }
                }
            }
        }

        workbook.write(outputStream);
        workbook.close();
    }

    public void exportById(OutputStream outputStream, Long id) throws IOException {
        List<Facture> allFactures = factureRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet;

        for(Facture f : allFactures) {
            if(f.getClient().getId() == id) {
                sheet = workbook.createSheet("Facture n°"+ f.getId());
                Row headerRow = sheet.createRow(0);
                Cell cellLibelle = headerRow.createCell(0);
                cellLibelle.setCellValue("Libellé");
                Cell cellQuantity = headerRow.createCell(1);
                cellQuantity.setCellValue("Quantité");
                Cell cellPrice = headerRow.createCell(2);
                cellPrice.setCellValue("Prix Unitaire");

                int loop = 1;

                for (LigneFacture ligne : f.getLigneFactures()) {
                    headerRow = sheet.createRow(loop);
                    Cell libelle = headerRow.createCell(0);
                    libelle.setCellValue(ligne.getArticle().getLibelle());
                    Cell quantite = headerRow.createCell(1);
                    quantite.setCellValue(ligne.getQuantite());
                    Cell prix = headerRow.createCell(2);
                    prix.setCellValue(ligne.getArticle().getPrix());

                    loop++;
                }
            }
        }

        workbook.write(outputStream);
        workbook.close();
    }

}
