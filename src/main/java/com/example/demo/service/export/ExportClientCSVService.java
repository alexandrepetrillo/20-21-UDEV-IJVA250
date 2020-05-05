package com.example.demo.service.export;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ClientDto;
import com.example.demo.entity.Client;
import com.example.demo.service.ClientService;

@Service
public class ExportClientCSVService {

	@Autowired
	private ClientService clientService;
	
	public void exportAll(PrintWriter writer) {
		
		/*génération d'un fichier CSV avec 2 colonnes : nom & prénom*/
		writer.println("Nom;Prénom;Age");
		
		List<ClientDto> listeClients = clientService.findAllClients();
		for (ClientDto client : listeClients) {
			//LocalDate dateNaissance = null;
			writer.println(client.nom + ";" + client.prenom + ";" + ((LocalDate.now().getYear()) - (client.dateNaissance.getYear())));
		}
	}
}
