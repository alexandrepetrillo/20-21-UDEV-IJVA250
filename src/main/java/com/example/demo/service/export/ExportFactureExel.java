package com.example.demo.service.export;

import com.example.demo.dto.FactureDto;
import com.example.demo.dto.LigneFactureDto;
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
public class ExportFactureExel {

    @Autowired
    private FactureService factureService;

    public void exportAll(OutputStream OutputStream) throws IOException {

        List<FactureDto> factures = factureService.findAllFactures();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Facture");

        Integer row = 0;

        Row firstHeaderRow = sheet.createRow(row);
        Cell firstCellId = firstHeaderRow.createCell(0);
        Cell firstCellClient = firstHeaderRow.createCell(1);
        Cell firstCellArticle = firstHeaderRow.createCell(2);
        Cell firstCellQuantite = firstHeaderRow.createCell(3);
        Cell firstCellPrix = firstHeaderRow.createCell(4);

        firstCellId.setCellValue("Numero facture");
        firstCellClient.setCellValue("Client");
        firstCellArticle.setCellValue("Article");
        firstCellQuantite.setCellValue("Quantité");
        firstCellPrix.setCellValue("Prix");

        for (FactureDto facture : factures) {
            List<LigneFactureDto> ligneFactureDtos = facture.getLigneFactures();

            for (LigneFactureDto ligne : ligneFactureDtos) {
                row = row + 1;
                Row headerRow = sheet.createRow(row);

                Cell CellId = headerRow.createCell(0);
                Cell CellClient = headerRow.createCell(1);
                Cell CellArticle = headerRow.createCell(2);
                Cell CellQuantite = headerRow.createCell(3);
                Cell CellPrix = headerRow.createCell(4);

                CellId.setCellValue(facture.getId());
                CellClient.setCellValue(facture.getClient().getNom());
                CellArticle.setCellValue(ligne.getArticle().getLibelle());
                CellQuantite.setCellValue(ligne.getQuantite());
                CellPrix.setCellValue(ligne.getArticle().getPrix());
            }
        }

        workbook.write(OutputStream);
        workbook.close();
    }

}
