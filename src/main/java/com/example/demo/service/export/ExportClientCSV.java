package com.example.demo.service.export;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.ClientDto;
import com.example.demo.service.ArticleService;
import com.example.demo.service.ClientService;

@Service
public class ExportClientCSV {

	 	@Autowired
	    private ClientService clientService;

	    public void exportAll(PrintWriter writer) {

	    	writer.println("Nom;Prenom;Age");
	    	List<ClientDto> cliList = clientService.findAllClients();
	    	for (ClientDto client : cliList) {
				writer.println(client.nom + ";" + client.prenom + ";" + client.age);
			}
	        
	    	//articleService.findAll().stream().forEach(art -> writer.println(art.libelle + ";" + art.prix));
	    	
	    }
	
}
