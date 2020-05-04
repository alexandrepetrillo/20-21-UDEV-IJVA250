package com.example.demo.service.export;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.ClientDto;
import com.example.demo.service.ArticleService;
import com.example.demo.service.ClientService;

@Service
public class ExportClientCSVService {
	
	@Autowired
    private ClientService clientService;

    public void exportAll(PrintWriter writer) {
    	List<ClientDto> toutLesClient = clientService.findAllClients();
        writer.println("Nom;Prénom;Age");
    	toutLesClient.stream().forEach(c ->writer.println(c.getNom()+";"+c.getPrenom()+
    			";"+(LocalDate.now().getYear()-c.getDateDeNaissance().getYear())+" ans"));
        

        // TODO remplacer par les vrais articles de la base de données, tips : rechercher tous les articles : articleService.findAll();
    }

}
