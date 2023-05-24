package com.example.rentacar.service;

import com.example.rentacar.model.Client;
import com.example.rentacar.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client getClientById(Long id){
        return clientRepository.getClientByIdClient(id);
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }
}
