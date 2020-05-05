package com.example.demo.service.export;

import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;

@Service
public class ExportClientCSV {

    @Autowired
    private ClientRepository clientRepository;

    public void exportAllClients(PrintWriter writer) {
        // Get a list of clients
        List<Client> clients= clientRepository.findAll();
        writer.println("Nom;Prenom;Date de Naissance");

        // Filled the cells with NOM PRENOM AGE
        for (Client client : clients){
            writer.println(client.getNom() + ";" + client.getPrenom() + ";" + client.getDateNaissance());
        }

    }

}
