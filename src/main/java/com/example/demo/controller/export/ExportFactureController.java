package com.example.demo.controller.export;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.export.ExportFactureExcelService;

@Controller
@RequestMapping("export")
public class ExportFactureController {

    @Autowired
    private ExportFactureExcelService exportFactureExcelService;
	
	
    @GetMapping("/clients/{id}/factures/xlsx")
    public void facturesClientXlsx(HttpServletRequest request, HttpServletResponse response, @PathVariable ("id") Long id) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-factures-clients.xlsx\"");
        OutputStream outputStream = response.getOutputStream();
        exportFactureExcelService.exportAllFacturesClient(outputStream, id);
    }
    
    @GetMapping("/factures/xlsx")
    public void AllFacturesXlsx(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-factures-clients.xlsx\"");
        OutputStream outputStream = response.getOutputStream();
        exportFactureExcelService.exportAllFactures(outputStream);
    }
    
}
