package br.com.udemy.controller;

import br.com.udemy.dto.ClientDTO;
import br.com.udemy.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDTO> store (@Valid @RequestBody ClientDTO clientDTO) {
        return new ResponseEntity(clientService.store(clientDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public List<ClientDTO> listAllClients () {
        return clientService.listAllClient();
    }

    @GetMapping("/{id}")
    public ClientDTO findById (@PathVariable("id") Integer id ) {
        return clientService.findById(id);
    }
}
