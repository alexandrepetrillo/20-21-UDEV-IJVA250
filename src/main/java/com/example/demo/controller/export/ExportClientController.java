package com.example.demo.controller.export;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.export.ExportClientCSVService;
import com.example.demo.service.export.ExportClientXLSXService;
import com.example.demo.service.export.ExportFactureXLSXService;

@Controller
@RequestMapping("/")
public class ExportClientController {
	
	@Autowired
	private ExportClientCSVService exportClientCSVService;
	
	@Autowired
	private ExportClientXLSXService exportClientXLSXService;
	
	@Autowired
	private ExportFactureXLSXService exportFactureXLSXService;
	
	@GetMapping("export/clients/csv")
    public void articlesCSV(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-clients-avec-age.csv\"");

        PrintWriter writer = response.getWriter();

        exportClientCSVService.exportAll(writer);
    }
	
	@GetMapping("export/clients/xlsx")
    public void articlesXLSX(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-clients.xlsx\"");

        OutputStream outputStream = response.getOutputStream();

        exportClientXLSXService.exportAll(outputStream);
    }
	
	@GetMapping("export/clients/{id}/factures/xlsx")
    public void facturesXLSX(HttpServletRequest request, HttpServletResponse response,@PathVariable("id") Long id) throws IOException {
    	response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-clients-facture.xlsx\"");

        OutputStream outputStream = response.getOutputStream();

        exportFactureXLSXService.exportById(outputStream, id); 
        }

}
