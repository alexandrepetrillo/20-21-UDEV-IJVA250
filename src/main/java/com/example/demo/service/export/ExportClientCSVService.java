package com.example.demo.service.export;

import com.example.demo.dto.ClientDto;
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

    public void exportAll(PrintWriter writer) {
        List<ClientDto> allClients = clientService.findAllClients();
        writer.println("Nom;Prénom;Âge");
        for(ClientDto client : allClients) {
            int age = LocalDate.now().getYear() - client.dateNaissance.getYear();
            writer.println(client.nom + ";" + client.prenom + ";" + age + " ans");
        }
    }
}
