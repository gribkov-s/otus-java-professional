package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.dao.ClientDao;
import ru.otus.dto.ClientDto;
import ru.otus.model.Client;
import java.util.List;

@Controller
public class ClientRestController {

    private final ClientDao clientDao;

    public ClientRestController(ClientDao clientService) {
        this.clientDao = clientService;
    }

    @PostMapping("/crm/clients")
    public String saveClient(Model model, @RequestBody ClientDto clientDto) {
        Client client = new Client(clientDto);
        clientDao.saveClient(client);
        return "redirect:/crm/clients";
    }

    @SuppressWarnings("squid:S4488")
    @RequestMapping(method = RequestMethod.GET, value = "/crm/clients")
    public String getClients(Model model) {
        List<ClientDto> clients = clientDao.findAll()
                .stream()
                .map(ClientDto::new)
                .toList();
        model.addAttribute("clients", clients);
        return "clients";
    }
}
