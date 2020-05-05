package com.example.demo.service.export;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ClientDto;
import com.example.demo.service.ClientService;

@Service
public class ExportClientCSVService {

	@Autowired
	private ClientService clientService;

	public void exportAll(PrintWriter writer) {

		List<ClientDto> data = clientService.findAllClients();
		writer.println("Nom;Prénom");
		for (ClientDto client : data) {
			writer.println(client.getNom() + ";" + client.getPrenom());
		}

	}

	public void exportAllWithAge(PrintWriter writer) {

		List<ClientDto> data = clientService.findAllClients();
		writer.println("Nom;Prénom;Age");
		for (ClientDto client : data) {
			writer.println(client.getNom() + ";" + client.getPrenom() + ";" + client.getAge());
		}

	}

}
