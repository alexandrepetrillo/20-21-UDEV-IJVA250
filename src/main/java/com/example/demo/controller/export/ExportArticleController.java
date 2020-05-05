package com.example.demo.controller.export;

import com.example.demo.service.export.ExportArticleCSV;
import com.example.demo.service.export.ExportArticleExcelService;
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
 * Controller pour réaliser l'export des articles.
 */
@Controller
@RequestMapping("export")
public class ExportArticleController {

    @Autowired
    private ExportArticleCSV exportArticleCSV;

    @Autowired
    private ExportArticleExcelService exportArticleExcel;

    /**
     * Export des articles au format CSV, déclenché sur l'url http://.../export/articles/csv
     *
     * @param request  objet reprensantant la requête http
     * @param response objet reprensantant la réponse http
     */
    @GetMapping("/articles/csv")
    public void articlesCSV(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // positionne de metadata sur la réponse afin d'informer le navigateur que la réponse correspond à un fichier téléchargeable.
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-articles.csv\"");

        // Le writter est un objet provenant de la response dans lequel on va pouvoir écrire pour générer le contenu de l'export CSV.
        PrintWriter writer = response.getWriter();

        exportArticleCSV.exportAll(writer);
    }

    @GetMapping("/articles/xlsx")
    public void articleXlsx(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-articles.xlsx\"");
        OutputStream outputStream = response.getOutputStream();
        exportArticleExcel.exportAll(outputStream);
    }

}

