package com.example.demo.controller.export;

import com.example.demo.service.export.ExportClientCSVService;
import com.example.demo.service.export.ExportClientXLSXService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Controller pour réaliser l'export des clients.
 */
@Controller
@RequestMapping("export")	
public class ExportClientController {

    @Autowired
    private ExportClientCSVService exportClientCSVService;
    
    @Autowired
    private ExportClientXLSXService exportClientXLSXService;

    /**
     * Export des clients au format CSV, déclenché sur l'url http://.../export/clients/csv
     *
     * @param request  objet reprensantant la requête http
     * @param response objet reprensantant la réponse http
     */
    @GetMapping("/clients/csv")
    public void clientsCSV(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // positionne de metadata sur la réponse afin d'informer le navigateur que la réponse correspond à un fichier téléchargeable.
        response.setContentType("text/csv");
        //response.setHeader("Content-Disposition", "attachment; filename=\"export-clients.csv\"");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-clients-avec-age.csv\"");

        // Le writter est un objet provenant de la response dans lequel on va pouvoir écrire pour générer le contenu de l'export CSV.
        PrintWriter writer = response.getWriter();

        //exportClientCSVService.exportAll(writer);
        exportClientCSVService.exportAllWithAge(writer);
    }
    
    /**
     * Export des articles au format XLSX, déclenché sur l'url http://.../export/clients/xlsx
     *
     * @param request  objet reprensantant la requête http
     * @param response objet reprensantant la réponse http
     */
    @GetMapping("/clients/xlsx")
    public void articlesXLSX(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-clients.xlsx\"");

        OutputStream outputStream = response.getOutputStream();
        
        exportClientXLSXService.exportAll(outputStream);
    }

}
