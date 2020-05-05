package com.example.demo.service.export;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.ClientDto;
import com.example.demo.service.ArticleService;
import com.example.demo.service.ClientService;


@Service
public class ExportClientCSV {

    @Autowired
    private ClientService clientService;

    public void exportAllClient(PrintWriter writer) {

        //génération d'un fichier CSV exemples avec 2 colonnes et 4 lignes
        writer.println("NOM;PRENOM;AGE");

        List<ClientDto> listAllClients = this.clientService.findAllClients();
        for (ClientDto clientDto : listAllClients) {
            writer.println(clientDto.nom+";"+clientDto.prenom+";"+clientDto.age);
		}

    }

}