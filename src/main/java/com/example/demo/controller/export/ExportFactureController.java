package com.example.demo.controller.export;

import com.example.demo.service.export.ExportFactureExcelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Controller pour réaliser l'export des factures.
 */
@Controller
@RequestMapping("export")
public class ExportFactureController {
    
    @Autowired
    private ExportFactureExcelService exportFactureExcelService;
    
    /**
     * Export des factures au format Excel, déclenché sur l'url http://.../export/factures/xlsx
     * 
     * @param request : objet reprensantant la requête http
     * @param response : objet reprensantant la réponse http
     * @throws IOException : l'exception levée en cas d'erreur
     */
    @GetMapping("/factures/xlsx")
    public void facturesXlsx(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-factures.xlsx\"");
        OutputStream outputStream = response.getOutputStream();
        exportFactureExcelService.exportAll(outputStream);
    }

}
