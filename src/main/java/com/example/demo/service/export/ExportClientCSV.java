package com.example.demo.service.export;

import com.example.demo.dto.ClientDto;
import com.example.demo.service.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;

@Service
public class ExportClientCSV {

    @Autowired
    private ClientService clientService;

    public void exportAll(PrintWriter writer) {

        writer.println("Nom;Prenom;Age");

        List<ClientDto> clients = clientService.findAllClients();

        for (ClientDto clientsDto : clients) {
            writer.println(clientsDto.getNom() + ";" + clientsDto.getPrenom() + ";" + clientsDto.getAge() + " ans");
        }
    }

}
