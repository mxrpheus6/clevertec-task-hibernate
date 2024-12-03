package by.clevertec.controller;

import by.clevertec.entity.Car;
import by.clevertec.entity.Client;
import by.clevertec.service.CarService;
import by.clevertec.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client-api")
public class ClientController {

    private final ClientService clientService;
    private final CarService carService;

    public ClientController(ClientService clientService, CarService carService) {
        this.clientService = clientService;
        this.carService = carService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @PostMapping("/add-client")
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        Client createdClient = clientService.addClient(client);
        return ResponseEntity.ok(createdClient);
    }

    @PostMapping("/buy-car/client={clientId}&car={carId}")
    public ResponseEntity<Client> buyCar(@PathVariable Long clientId, @PathVariable Long carId) {
        Client client = clientService.getClientById(clientId);
        Car car = carService.getCarById(carId);

        client.getCars().add(car);

        carService.updateCar(car);
        return ResponseEntity.ok(client);
    }
}
