package com.example.demo.controller.export;

import com.example.demo.service.export.ExportFactureExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/export")
public class ExportFactureController {

    @Autowired
    private ExportFactureExcelService exportFactureExcelService;

    @GetMapping("/factures/xlsx")
    public void facturesXlsx(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-factures.xlsx\"");

        // Le writter est un objet provenant de la response dans lequel on va pouvoir écrire pour générer le contenu de l'export CSV.
        OutputStream outputStream = response.getOutputStream();

        exportFactureExcelService.exportAll(outputStream);
    }

  @GetMapping("/clients/{id}/factures/xlsx")
    public void singleClientFactures(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Long id) throws IOException {
      response.setContentType("application/vnd.ms-excel");
      response.setHeader("Content-Disposition", "attachment; filename=\"export-factures.xlsx\"");

      // Le writter est un objet provenant de la response dans lequel on va pouvoir écrire pour générer le contenu de l'export CSV.
      OutputStream outputStream = response.getOutputStream();

      exportFactureExcelService.exportById(outputStream, id);
  }

}
