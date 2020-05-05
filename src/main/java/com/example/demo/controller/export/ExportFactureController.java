package com.example.demo.controller.export;

import com.example.demo.service.export.ExportFactureService;

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
    private ExportFactureService exportFactureService;

    @GetMapping("/factures/xlsx")
    public void articlesXlsx(HttpServletRequest request, HttpServletResponse response) throws IOException {


    }
}
