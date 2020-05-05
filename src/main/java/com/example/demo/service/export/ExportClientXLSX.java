package com.example.demo.service.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  

import org.apache.poi.ss.usermodel.CellStyle;  
import org.apache.poi.ss.usermodel.Font;  
 

import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.ClientDto;
import com.example.demo.service.ArticleService;
import com.example.demo.service.ClientService;

@Service
public class ExportClientXLSX {
	@Autowired
    private ClientService clientService;

    public void exportAll(OutputStream outputStream) throws IOException {
    	//cell properties
    	HashMap<String, Object> properties = new HashMap<String, Object>();  
        //border
        properties.put(CellUtil.BORDER_TOP, BorderStyle.THICK);  
        properties.put(CellUtil.BORDER_BOTTOM, BorderStyle.THICK);  
        properties.put(CellUtil.BORDER_LEFT, BorderStyle.THICK);  
        properties.put(CellUtil.BORDER_RIGHT, BorderStyle.THICK);  
        //colors 
        properties.put(CellUtil.TOP_BORDER_COLOR, IndexedColors.BLUE.getIndex());  
        properties.put(CellUtil.BOTTOM_BORDER_COLOR, IndexedColors.BLUE.getIndex());  
        properties.put(CellUtil.LEFT_BORDER_COLOR, IndexedColors.BLUE.getIndex());  
        properties.put(CellUtil.RIGHT_BORDER_COLOR, IndexedColors.BLUE.getIndex());  
        
            
        
    	//init work book
    	Workbook workbook = new XSSFWorkbook();
    	Sheet sheet = workbook.createSheet("Clients");
    	
    	//font
        
        CellStyle style = workbook.createCellStyle(); 
        // Creating Font and settings  
        XSSFFont  font = (XSSFFont) workbook.createFont();  
        font.setFontHeightInPoints((short)10);  
        font.setFontName("Helvetica");  
        font.setBold(true);  
        font.setColor(new XSSFColor(new java.awt.Color(255, 0, 255)));
        // Applying font to the style  
        style.setFont(font);  
        
    	
    	//init first row
    	Row headerRow = sheet.createRow(0);
    	Cell cell = headerRow.createCell(0);
    	cell.setCellValue("Nom");
    	cell.setCellStyle(style);  
    	CellUtil.setCellStyleProperties(cell, properties); 
    	Cell cell2 = headerRow.createCell(1);
    	cell2.setCellValue("Prenom");
    	cell2.setCellStyle(style);  
    	CellUtil.setCellStyleProperties(cell2, properties); 
    	Cell cell3 = headerRow.createCell(2);
    	cell3.setCellValue("Age");
    	cell3.setCellStyle(style);  
    	CellUtil.setCellStyleProperties(cell3, properties); 
    	
    	
    	
    	//go through clients
    	int i = 1;
    	List<ClientDto> cliList = clientService.findAllClients();
    	for (ClientDto client : cliList) {
			//(client.nom + ";" + client.prenom + ";" + client.age);
    		Row headerRowN = sheet.createRow(i);
    		Cell cellN = headerRowN.createCell(0);
        	cellN.setCellValue(client.nom);
        	CellUtil.setCellStyleProperties(cellN, properties);  
        	Cell cellP = headerRowN.createCell(1);
        	cellP.setCellValue(client.prenom);
        	CellUtil.setCellStyleProperties(cellP, properties);  
        	Cell cellA = headerRowN.createCell(2);
        	cellA.setCellValue(client.age);
        	CellUtil.setCellStyleProperties(cellA, properties);  
        	i++;
		}
    	
    	
    	workbook.write(outputStream);
    	workbook.close();
    	
    }
}
