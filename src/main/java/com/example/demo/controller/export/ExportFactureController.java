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

@Controller
@RequestMapping("export")
public class ExportFactureController {

    @Autowired
    private ExportFactureExcelService exportFactureExcelService;

    @GetMapping("/factures/xlsx")
    public void articlesXLSX(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-factures.xlsx\"");

        OutputStream outputStream = response.getOutputStream();

        exportFactureExcelService.exportAll(outputStream);
    }





}
