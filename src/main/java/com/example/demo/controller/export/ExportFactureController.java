package com.example.demo.controller.export;

import com.example.demo.service.export.ExportFactureExel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Controller pour réaliser l'export des articles.
 */
@Controller
@RequestMapping("export")
public class ExportFactureController {

    @Autowired
    private ExportFactureExel exportFactureExel;

    /**
     * Export des factures au format exel, déclenché sur l'url http://.../export/factures/xlsx
     *
     * @param request  objet reprensantant la requête http
     * @param response objet reprensantant la réponse http
     */
    @GetMapping("/factures/xlsx")
    public void articlesXlsx(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/vnd.ms-exel");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-factures.xlsx\"");

        OutputStream outputStream = response.getOutputStream();

        exportFactureExel.exportAll(outputStream);
    }
}
