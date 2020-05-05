package com.example.demo.service.export;


import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.ClientDto;
import com.example.demo.dto.FactureDto;
import com.example.demo.dto.LigneFactureDto;
import com.example.demo.entity.Article;
import com.example.demo.entity.Client;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.FactureRepository;
import com.example.demo.service.ArticleService;
import com.example.demo.service.ClientService;
import com.example.demo.service.FactureService;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
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
public class ExportFactureExcelService {

    @Autowired
    private FactureRepository factureRepository;
    @Autowired
    private FactureService factureService;

    public void exportAllFacturesClient(OutputStream outputStream, long id) throws IOException {
//        List<Client> allClients = clientRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        
        // Styles des cellules: 
        CellStyle styleGreen = workbook.createCellStyle();
        styleGreen.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        styleGreen.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
        
        CellStyle styleBlue = workbook.createCellStyle();
        styleBlue.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        styleBlue.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
        
        CellStyle styleYellow = workbook.createCellStyle();
        styleYellow.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        styleYellow.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
        
        Font font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        styleGreen.setFont(font);
        styleBlue.setFont(font);
        styleYellow.setFont(font);
        
             
        
        List<FactureDto> listAllFacturesAllClient = this.factureService.findAllFactures();
        
        
        for (FactureDto factureDto : listAllFacturesAllClient) {
			if(factureDto.getClient().id == id) {
	            //SHEET
	            Sheet sheet = workbook.createSheet("Facture"+factureDto.getId());
	            Row headerRow = sheet.createRow(0);
	            //LIBELLES
	            Cell cellLibelle0 = headerRow.createCell(0);
	            Cell cellLibelle1 = headerRow.createCell(1);
	            Cell cellLibelle2 = headerRow.createCell(2);
	            Cell cellLibelle3 = headerRow.createCell(3);
	            cellLibelle0.setCellValue("Désignation");
	            cellLibelle1.setCellValue("Quantité");
	            cellLibelle2.setCellValue("Prix Unitaire");
	            cellLibelle3.setCellValue("Prix ligne");
	            cellLibelle0.setCellStyle(styleGreen);
	            cellLibelle1.setCellStyle(styleBlue);
	            cellLibelle2.setCellStyle(styleYellow);
	            cellLibelle3.setCellStyle(styleGreen);

	            for (int i =0; i<factureDto.getLigneFactures().size(); i++) {
	            		            	
	                Row newRow = sheet.createRow(i+1);
	                
	                Cell firstCell = newRow.createCell(0);
	                firstCell.setCellStyle(styleGreen);
	                firstCell.setCellValue(factureDto.getLigneFactures().get(i).getArticle().getLibelle());
	                
	                Cell secondCell =newRow.createCell(1);
	                secondCell.setCellValue(factureDto.getLigneFactures().get(i).getQuantite());
	                secondCell.setCellStyle(styleBlue);
	                
	                Cell thirdCell =newRow.createCell(3);
	                thirdCell.setCellValue(factureDto.getLigneFactures().get(i).getArticle().getPrix());
	                thirdCell.setCellStyle(styleYellow);
	                
	                Cell fourthCell =newRow.createCell(3);
	                fourthCell.setCellValue(
	                		(factureDto.getLigneFactures().get(i).getArticle().getPrix())*(factureDto.getLigneFactures().get(i).getQuantite())
	                		);
	                fourthCell.setCellStyle(styleGreen);
	    		}
			}
		}

        workbook.write(outputStream);
        workbook.close();
    }
    
    
    
    
    
    public void exportAllFactures(OutputStream outputStream) throws IOException {
//      List<Client> allClients = clientRepository.findAll();

      Workbook workbook = new XSSFWorkbook();
      
      // Styles des cellules: 
      CellStyle styleGreen = workbook.createCellStyle();
      styleGreen.setFillForegroundColor(IndexedColors.GREEN.getIndex());
      styleGreen.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
      
      CellStyle styleBlue = workbook.createCellStyle();
      styleBlue.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
      styleBlue.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
      
      CellStyle styleYellow = workbook.createCellStyle();
      styleYellow.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
      styleYellow.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
      
      Font font = workbook.createFont();
      font.setColor(IndexedColors.WHITE.getIndex());
      styleGreen.setFont(font);
      styleBlue.setFont(font);
      styleYellow.setFont(font);
      
           
      
      List<FactureDto> listAllFacturesAllClient = this.factureService.findAllFactures();
      
      
      for (FactureDto factureDto : listAllFacturesAllClient) {
	            //SHEET
	            Sheet sheet = workbook.createSheet("Facture"+factureDto.getId());
	            Row headerRow = sheet.createRow(0);
	            //LIBELLES
	            Cell cellLibelle0 = headerRow.createCell(0);
	            Cell cellLibelle1 = headerRow.createCell(1);
	            Cell cellLibelle2 = headerRow.createCell(2);
	            Cell cellLibelle3 = headerRow.createCell(3);
	            cellLibelle0.setCellValue("Désignation");
	            cellLibelle1.setCellValue("Quantité");
	            cellLibelle2.setCellValue("Prix Unitaire");
	            cellLibelle3.setCellValue("Prix ligne");
	            cellLibelle0.setCellStyle(styleGreen);
	            cellLibelle1.setCellStyle(styleBlue);
	            cellLibelle2.setCellStyle(styleYellow);
	            cellLibelle3.setCellStyle(styleGreen);

	            for (int i =0; i<factureDto.getLigneFactures().size(); i++) {
	            		            	
	                Row newRow = sheet.createRow(i+1);
	                
	                Cell firstCell = newRow.createCell(0);
	                firstCell.setCellStyle(styleGreen);
	                firstCell.setCellValue(factureDto.getLigneFactures().get(i).getArticle().getLibelle());
	                
	                Cell secondCell =newRow.createCell(1);
	                secondCell.setCellValue(factureDto.getLigneFactures().get(i).getQuantite());
	                secondCell.setCellStyle(styleBlue);
	                
	                Cell thirdCell =newRow.createCell(3);
	                thirdCell.setCellValue(factureDto.getLigneFactures().get(i).getArticle().getPrix());
	                thirdCell.setCellStyle(styleYellow);
	                
	                Cell fourthCell =newRow.createCell(3);
	                fourthCell.setCellValue(
	                		(factureDto.getLigneFactures().get(i).getArticle().getPrix())*(factureDto.getLigneFactures().get(i).getQuantite())
	                		);
	                fourthCell.setCellStyle(styleGreen);
	    		}
			}
		

      workbook.write(outputStream);
      workbook.close();
  }
    

}

