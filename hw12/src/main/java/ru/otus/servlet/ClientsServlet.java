package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.dao.ClientDao;
import ru.otus.dto.ClientDto;
import ru.otus.model.Client;
import ru.otus.services.TemplateProcessor;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClientsServlet extends HttpServlet {

    private static final String CLIENTS_PAGE_TEMPLATE = "clients.html";
    private static final String TEMPLATE_ATTR_CLIENTS = "clients";

    private final ClientDao clientDao;
    private final TemplateProcessor templateProcessor;
    private final Gson gson;

    public ClientsServlet(ClientDao clientDao,
                          TemplateProcessor templateProcessor,
                          Gson gson) {
        this.clientDao = clientDao;
        this.templateProcessor = templateProcessor;
        this.gson = gson;
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Client> clients = clientDao.findAll();
        List<ClientDto> clientsDto = clients.stream()
                .map(cl -> new ClientDto(cl.clone()))
                .toList();
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(TEMPLATE_ATTR_CLIENTS, clientsDto);
        resp.setContentType("text/html");
        resp.getWriter().println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String clientJson = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ClientDto clientDto = gson.fromJson(clientJson, ClientDto.class);
        Client client = new Client(clientDto);
        clientDao.saveClient(client);
        resp.setContentType("application/json;charset=UTF-8"); ///
        resp.setStatus(200);
    }
}
