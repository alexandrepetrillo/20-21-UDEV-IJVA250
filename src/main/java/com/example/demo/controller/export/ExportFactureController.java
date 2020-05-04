package com.example.demo.controller.export;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.export.ExportFactureXLSXService;

@Controller
public class ExportFactureController {
	
	@Autowired
	ExportFactureXLSXService exportFactureXLSXService;
	
	@GetMapping("export/factures/xlsx")
    public void factureXLSX(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-factures.xlsx\"");

        OutputStream outputStream = response.getOutputStream();

        exportFactureXLSXService.exportAll(outputStream);
    }

}
