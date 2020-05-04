package com.example.demo.service.export;

import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class ExportClientCSVService {

    @Autowired
    private ClientRepository clientRepository;

    /**
     * Méthode qui exporte les clients au format CSV
     * 
     * @param writer : le flux dans lequel on écrit
     */
    public void exportAll(PrintWriter writer) {
        //génération d'un fichier CSV exemples avec 2 colonnes et 4 lignes
        writer.println("Nom;Prénom;Age");

        List<Client> allClients = clientRepository.findAll();
        for (Client client : allClients) {
        	Period difference = Period.between(client.getDateNaissance(), LocalDate.now());
    		Integer ageClient = difference.getYears();
    		if (client.getDateNaissance().getMonthValue() > LocalDate.now().getMonthValue()) {
    			ageClient = ageClient + 1;
    		} else {
    			ageClient = ageClient - 1;
    		}
            writer.println(client.getNom() + ";" + client.getPrenom() + ";" + ageClient + " ans");
        }
    }

}
