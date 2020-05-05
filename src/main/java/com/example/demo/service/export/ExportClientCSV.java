package com.example.demo.service.export;

import com.example.demo.dto.ClientDto;
import com.example.demo.service.ArticleService;
import com.example.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class ExportClientCSV {

    @Autowired
    private ClientService clientService;

    public void exportAll(PrintWriter writer) {
        writer.println("Nom;Prénom");
        List<ClientDto> allClient = clientService.findAllClients();
        for (ClientDto client : allClient){
            writer.println(client.nom + ";" + client.prenom);
        }
    }

    public void exportAllAge(PrintWriter writer) {
        LocalDate today = LocalDate.now();
        writer.println("Nom;Prénom;Age");
        List<ClientDto> allClient = clientService.findAllClients();
        for (ClientDto client : allClient){
            writer.println(client.nom + ";" + client.prenom + ";" + Period.between(client.dateNaissance, today).getYears());
        }
    }
}
