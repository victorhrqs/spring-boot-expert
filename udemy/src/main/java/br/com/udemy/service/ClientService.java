package br.com.udemy.service;

import br.com.udemy.dto.ClientDTO;
import br.com.udemy.entities.Client;
import br.com.udemy.exception.NotFoundException;
import br.com.udemy.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public ClientDTO store(ClientDTO clientDTO) {

        Client c = new Client();

        c.setName(clientDTO.getName());
        c.setEmail(clientDTO.getEmail());

        Client client = clientRepository.save(c);

        return ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .build();
    }

    public List<ClientDTO> listAllClient () {
        return clientRepository.findAll().stream().map( c -> {
            ClientDTO clientDTO = new ClientDTO();

            clientDTO.setId(c.getId());
            clientDTO.setName(c.getName());
            clientDTO.setEmail(c.getEmail());

            return clientDTO;
        }).collect(Collectors.toList());
    }

    public ClientDTO findById (Integer id) {
        Optional<Client> user = clientRepository.findById(id);

        if (user.isEmpty()) throw new NotFoundException("User not found");

        return ClientDTO.builder()
                .id(user.get().getId())
                .name(user.get().getName())
                .email(user.get().getEmail())
                .build();
    }
}
