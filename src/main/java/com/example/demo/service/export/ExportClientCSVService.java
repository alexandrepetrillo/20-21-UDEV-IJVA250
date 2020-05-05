package com.example.demo.service.export;

import com.example.demo.dto.ClientDto;
import com.example.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class ExportClientCSVService {
    @Autowired
    private ClientService clientService;

    public void exportAll(PrintWriter writer) {

        writer.println("Nom;Prénom;Age");
        List<ClientDto> allClients = clientService.findAllClients();

        for (ClientDto client : allClients) {
            Period ecart = Period.between(client.getDateNaissance(), LocalDate.now());
            Integer age = ecart.getYears();
            writer.println(client.nom+";"+client.prenom+";"+age + " ans");
        }
    }
}
