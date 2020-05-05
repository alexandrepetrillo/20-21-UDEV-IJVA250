package com.example.demo.service.export;

import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.entity.LigneFacture;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

@Service
public class ExportClientCSVService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private FactureRepository factureRepository;

    public void exportAll(PrintWriter writer) {
        writer.println("Nom;Prénom;Ses factures");

        List<Client> allClients = clientRepository.findAll();
        for (Client client : allClients) {

            //Set<LigneFacture> allFactures = this.findAllFactures(client);

            String sep = ";";
            writer.println(client.getNom() + sep + client.getPrenom() + sep + "factures ici" );
        }
    }

    /*private Set<LigneFacture> findAllFactures(Client client) {
        return factureRepository.findFacturesByClient(client); //retourne la liste des factures pour le client "idClient"
    }*/
}
