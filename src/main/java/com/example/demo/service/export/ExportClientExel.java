package com.example.demo.service.export;

import com.example.demo.dto.ClientDto;
import com.example.demo.dto.FactureDto;
import com.example.demo.dto.LigneFactureDto;
import com.example.demo.entity.Client;
import com.example.demo.service.ClientService;
import com.example.demo.service.FactureService;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
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
public class ExportClientExel {

    @Autowired
    private ClientService clientService ;
    @Autowired
    private FactureService factureService ;

    public void exportAll(OutputStream OutputStream) throws IOException {

        List<ClientDto> clients = clientService.findAllClients();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Clients");
        
        CellStyle style = workbook.createCellStyle();
        CellStyle normalStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        
        font.setColor(HSSFColor.HSSFColorPredefined.LAVENDER.getIndex());
        style.setFont(font);
        style.setBorderTop(BorderStyle.THICK);
        style.setBorderBottom(BorderStyle.THICK);
        style.setBorderLeft(BorderStyle.THICK);
        style.setBorderRight(BorderStyle.THICK);
        style.setBottomBorderColor(IndexedColors.AQUA.getIndex());
        style.setTopBorderColor(IndexedColors.AQUA.getIndex());
        style.setLeftBorderColor(IndexedColors.AQUA.getIndex());
        style.setRightBorderColor(IndexedColors.AQUA.getIndex());

        normalStyle.setBorderTop(BorderStyle.THICK);
        normalStyle.setBorderBottom(BorderStyle.THICK);
        normalStyle.setBorderLeft(BorderStyle.THICK);
        normalStyle.setBorderRight(BorderStyle.THICK);

        normalStyle.setBottomBorderColor(IndexedColors.AQUA.getIndex());
        normalStyle.setTopBorderColor(IndexedColors.AQUA.getIndex());
        normalStyle.setLeftBorderColor(IndexedColors.AQUA.getIndex());
        normalStyle.setRightBorderColor(IndexedColors.AQUA.getIndex());
        
        Integer row = 0;

        Row firstHeaderRow = sheet.createRow(row);

        Cell firstCellPrenom = firstHeaderRow.createCell(0);
        Cell firstCellNom = firstHeaderRow.createCell(1);
        Cell firstCellAge = firstHeaderRow.createCell(2);

        firstCellPrenom.setCellValue("Prenom");
        firstCellNom.setCellValue("Nom");
        firstCellAge.setCellValue("Age");

        firstCellPrenom.setCellStyle(style);
        firstCellNom.setCellStyle(style);
        firstCellAge.setCellStyle(style);

        for (ClientDto clientDto : clients) {
            row = row + 1;
            Row headerRow = sheet.createRow(row);

            Cell cellPrenom = headerRow.createCell(0);
            Cell cellNom = headerRow.createCell(1);
            Cell cellAge = headerRow.createCell(2);

            cellPrenom.setCellValue(clientDto.getPrenom());
            cellNom.setCellValue(clientDto.getNom());
            cellAge.setCellValue(clientDto.getAge() + " ans");

            cellPrenom.setCellStyle(normalStyle);
            cellNom.setCellStyle(normalStyle);
            cellAge.setCellStyle(normalStyle);
        }
        
        workbook.write(OutputStream);
        workbook.close();
    }

    public void exportOneWithInvoice(OutputStream OutputStream, Long clientId) throws IOException {
        Client client = clientService.findClientById(clientId);
        
        List<FactureDto> factures = factureService.findAllByCLient(client);

        ClientDto clientDto = factures.get(0).getClient();

        //Creation premier feuille : CLients 
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Client");

        Row nameRow = sheet.createRow(0);
        Cell nameCell = nameRow.createCell(0);
        Cell nameCellClient = nameRow.createCell(1);

        nameCell.setCellValue("Nom");
        nameCellClient.setCellValue(clientDto.getNom());

        Row prenomRow = sheet.createRow(1);
        Cell prenomCell = prenomRow.createCell(0);
        Cell prenomCellClient = prenomRow.createCell(1);

        prenomCell.setCellValue("Prenom");
        prenomCellClient.setCellValue(clientDto.getPrenom());

        Row birthRow = sheet.createRow(2);
        Cell birthCell = birthRow.createCell(0);
        Cell birthCellClient = birthRow.createCell(1);

        birthCell.setCellValue("Date de naissance");
        birthCellClient.setCellValue(clientDto.getDateNaisance().toString());

        for (FactureDto facture : factures) {
            Sheet factureSheet = workbook.createSheet("Facture n" + facture.getId());

            Integer row = 0;

            Row firstRow = factureSheet.createRow(row);
            Cell labelDesignationCell = firstRow.createCell(0);
            Cell labelQuantiteCell = firstRow.createCell(1);
            Cell labelPrixCell = firstRow.createCell(2);

            labelDesignationCell.setCellValue("Designation");
            labelQuantiteCell.setCellValue("Quantité");
            labelPrixCell.setCellValue("Prix unitaire");

            List<LigneFactureDto> ligneFactureDtos = facture.getLigneFactures();

            for (LigneFactureDto ligne : ligneFactureDtos) {
                row = row + 1;
                Row headerRow = factureSheet.createRow(row);

                Cell cellDesignation = headerRow.createCell(0);
                Cell cellQuantite = headerRow.createCell(1);
                Cell cellPrix = headerRow.createCell(2);

                cellDesignation.setCellValue(ligne.getArticle().getLibelle());
                cellQuantite.setCellValue(ligne.getQuantite());
                cellPrix.setCellValue(ligne.getArticle().getPrix());
            }
        }

        workbook.write(OutputStream);
        workbook.close();
    }
}
