package com.example.demo.service.export;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;
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

		writer.println("Nom;Prénom;Age");

		List<Client> allClients = clientRepository.findAll();
		for (Client client : allClients) {
			writer.println(client.getNom() + ";" + client.getPrenom() + ";"
					+ Period.between(client.getDateNaissance(), LocalDate.now()).getYears() + " ans");
		}

	}

}
