package by.clevertec.controller;

import by.clevertec.entity.Car;
import by.clevertec.entity.Client;
import by.clevertec.entity.Review;
import by.clevertec.service.CarService;
import by.clevertec.service.ClientService;
import by.clevertec.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/review-api")
public class ReviewController {

    private final ReviewService reviewService;
    private final ClientService clientService;
    private final CarService carService;

    public ReviewController(ReviewService reviewService,
                            ClientService clientService,
                            CarService carService) {
        this.reviewService = reviewService;
        this.clientService = clientService;
        this.carService = carService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/get-all/search")
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false) Integer rating) {
        List<Review> reviews = reviewService.searchReviews(keyword, rating);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/add-review/client={clientId}/car={carId}")
    public ResponseEntity<Review> addReview(@PathVariable Long clientId,
                                            @PathVariable Long carId,
                                            @RequestBody Review review) {
        Client client = clientService.getClientById(clientId);
        Car car = carService.getCarById(carId);

        if (!client.getReviews().contains(review)) {
            client.getReviews().add(review);
        }

        Review reviewResult = reviewService.addReview(client, car, review);
        clientService.updateClient(client);

        return ResponseEntity.ok(reviewResult);
    }

}
