package com.example.demo.service.export;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ClientDto;
import com.example.demo.service.ClientService;

@Service
public class ExportClientCSV {

	@Autowired
	private ClientService clientService;
	
	public void exportAll(PrintWriter writer) {
		//génération d'un fichier CSV exemples avec 2 colonnes et 4 lignes
        writer.println("Nom;Prénom;Age");
        List<ClientDto> allClients = clientService.findAllClients();
        
//        for (ClientDto client : allClients) {
//        	writer.println(client.nom + ";" + client.prenom);
//        }
        
        for (ClientDto client : allClients) {
        	writer.println(client.nom + ";" + client.prenom + ";" + Period.between(client.dateNaissance, LocalDate.now()).getYears() + " ans");
        }
	}
	
}
