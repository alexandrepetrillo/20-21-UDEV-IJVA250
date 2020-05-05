package com.example.demo.service.export;

import com.example.demo.dto.ClientDto;
import com.example.demo.service.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class ExportClientCSVService {

    @Autowired
    private ClientService clientService;

    /**
     * Méthode qui exporte les clients au format CSV
     * 
     * @param writer : le flux dans lequel on écrit
     */
    public void exportAll(PrintWriter writer) {
        
        writer.println("LastName;FirstName;Age");

        List<ClientDto> allClients = clientService.findAllClients();
        
        for (ClientDto client : allClients) {
        	Integer clientAge = LocalDate.now().getYear() - client.getDateNaissance().getYear();        	
            writer.println(client.getNom() + ";" + client.getPrenom() + ";" + clientAge);
        }
    }

}