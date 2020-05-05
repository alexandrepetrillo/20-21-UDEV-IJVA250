package com.example.demo.controller.export;

import com.example.demo.service.export.ExportArticleCSVService;
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
    private ExportClientExcelService exportClientExcelService;
}
