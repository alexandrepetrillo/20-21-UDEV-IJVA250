package com.example.demo.service.export;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;

@Service
public class ExportClientCSVService {

	@Autowired
    private ClientRepository clientRepository;
	
	public void exportAll(PrintWriter writer) {
		writer.println("Nom;Prenom;Age");
		List<Client> allClients = clientRepository.findAll();
        for (Client client : allClients) {
        	int age = LocalDate.now().getYear() - client.getDateNaissance().getYear();
            writer.println(client.getNom() + ";" + client.getPrenom() + ";" + age);
        }
	}
}
