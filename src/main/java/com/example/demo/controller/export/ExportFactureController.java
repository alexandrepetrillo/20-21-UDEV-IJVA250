package com.example.demo.controller.export;

import com.example.demo.service.export.ExportClientCSV;
import com.example.demo.service.export.ExportClientExcelService;
import com.example.demo.service.export.ExportFactureExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

@Controller
@RequestMapping("export")
public class ExportFactureController {

    @Autowired
    private ExportFactureExcelService exportFactureExcelService;

    /**
     * Export des clients au format CSV, déclenché sur l'url http://.../export/clients/csv
     *
     * @param request  objet reprensantant la requête http
     * @param response objet reprensantant la réponse http
     */

    @GetMapping("/factures/xlsx")
    public void clientsXlsx(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // positionne de metadata sur la réponse afin d'informer le navigateur que la réponse correspond à un fichier téléchargeable.
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-factures.xlsx\"");

        // Le writter est un objet provenant de la response dans lequel on va pouvoir écrire pour générer le contenu de l'export CSV.
        //PrintWriter writer = response.getWriter();
        OutputStream write = response.getOutputStream();

        exportFactureExcelService.exportAllFactureXlsx(write);
    }

}
