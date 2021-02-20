package com.example.demo.controller.export;

import com.example.demo.service.export.ExportClientCSVService;
import com.example.demo.service.export.ExportClientExcelService;
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
public class ExportClientController {

    @Autowired
    private ExportClientCSVService exportClientCSVService;

    @GetMapping("/clients/csv")
    public void clientsCSV(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-clients.csv\"");
        PrintWriter writer = response.getWriter();

        exportClientCSVService.exportAll(writer);
    }

    @Autowired
    private ExportClientExcelService exportClientExcelService;

    @GetMapping("/clients/xlsx")
    public void clientXlsx(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"export-clients.xlsx\"");
        OutputStream outputStream = response.getOutputStream();
        exportClientExcelService.exportAll(outputStream);
    }
}

