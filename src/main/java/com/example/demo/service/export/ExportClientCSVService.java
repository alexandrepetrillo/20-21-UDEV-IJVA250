package com.example.demo.service.export;

import com.example.demo.dto.ClientDto;
import com.example.demo.entity.Client;
import com.example.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExportClientCSVService {

    @Autowired
    private ClientService clientService;

    public void exportAll(PrintWriter writer){


        List<ClientDto> listes = clientService.findAllClients();
        writer.println("Nom;Prenom;Age");

        for(ClientDto client : listes) {
        writer.println(client.nom + ";" + client.prenom + ";" +  client.dateNaissance.until(LocalDate.now()).getYears() + " ans");
        }
    }
}
