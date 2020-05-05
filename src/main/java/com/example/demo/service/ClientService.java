package com.example.demo.service;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ClientDto;
import com.example.demo.entity.Client;
import com.example.demo.repository.ClientRepository;

/**
 * Service contenant les actions métiers liées aux clients.
 */
@Service
@Transactional
public class ClientService {

    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<ClientDto> findAllClients() {
        // Transformation d'une liste de Client en ClientDto
        return clientRepository
                .findAll()
                .stream()
                .map(c -> new ClientDto(c.getId(), c.getNom(), c.getPrenom(), c.getDateNaissance()))
                .collect(toList());
    }
    
	public int getAgeFromLocalDateNaissance(Client cl) {
		return (LocalDate.now().getYear()) - (cl.getDateNaissance().getYear());
	}
	
	public Date convertToDateViaSqlDate(LocalDate dateToConvert) {
	    return java.sql.Date.valueOf(dateToConvert);
	}
}
