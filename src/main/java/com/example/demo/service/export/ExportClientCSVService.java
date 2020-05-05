package com.example.demo.service.export;

import com.example.demo.dto.ClientDto;
import com.example.demo.service.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;

@Service
public class ExportClientCSVService {

    @Autowired
    private ClientService clientService;

    public void exportAll(PrintWriter writer) {
    	List<ClientDto> clients = clientService.findAllClients();

        //génération d'un fichier CSV clients
        writer.println("Nom;Prénom");
        for (ClientDto client : clients) {
    		writer.println(client.nom+";"+client.prenom);
    	}
    }
    
    public void exportAllWithAge(PrintWriter writer) {
    	List<ClientDto> clients = clientService.findAllClients();

        //génération d'un fichier CSV clients
        writer.println("Nom;Prénom;Âge");
        for (ClientDto client : clients) {
    		writer.println(client.nom+";"+client.prenom+";"+client.getAge()+" ans");
    	}
        
    }

}
