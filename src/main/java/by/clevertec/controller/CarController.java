package by.clevertec.controller;

import by.clevertec.entity.Car;
import by.clevertec.entity.CarShowroom;
import by.clevertec.entity.Category;
import by.clevertec.entity.enums.SortingOrder;
import by.clevertec.service.CarService;
import by.clevertec.service.CarShowroomService;
import by.clevertec.service.CategoryService;
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
@RequestMapping("/car-api")
public class CarController {

    private final CarService carService;
    private final CarShowroomService carShowroomService;
    private final CategoryService categoryService;

    public CarController(CarService carService,
                         CarShowroomService carShowroomService,
                         CategoryService categoryService) {
        this.carService = carService;
        this.carShowroomService = carShowroomService;
        this.categoryService = categoryService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Car>> getAll(@RequestParam(required = false) SortingOrder priceOrder) {
        List<Car> cars = carService.getAllCars(priceOrder);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/get-all/filters")
    public ResponseEntity<List<Car>> getAllCarsWithFilters(@RequestParam(required = false) String brand,
                                                           @RequestParam(required = false) String category,
                                                           @RequestParam(required = false) Integer manufactureYear,
                                                           @RequestParam(required = false) Double minPrice,
                                                           @RequestParam(required = false) Double maxPrice) {
        List<Car> filteredCars = carService.getAllCarsWithFilters(brand, category, manufactureYear, minPrice, maxPrice);
        return ResponseEntity.ok(filteredCars);
    }

    @GetMapping("/get-all/pagination")
    public ResponseEntity<List<Car>> getAllCarsWithPagination(@RequestParam int page, @RequestParam int pageSize) {
        List<Car> paginatedCars = carService.getAllCarsWithPagination(page, pageSize);
        return ResponseEntity.ok(paginatedCars);
    }

    @PostMapping("/add-car")
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        Car createdCar = carService.addCar(car);
        return ResponseEntity.ok(createdCar);
    }

    @PostMapping("/assign-showroom/car={carId}&showroom={showroomId}")
    public ResponseEntity<Car> assignCarToShowroom(@PathVariable Long carId, @PathVariable Long showroomId) {
        Car car = carService.getCarById(carId);
        CarShowroom carShowroom = carShowroomService.getCarShowroomById(showroomId);

        car.setShowroom(carShowroom);
        car = carService.updateCar(car);
        return ResponseEntity.ok(car);
    }

    @PostMapping("/assign-category/car={carId}&category={categoryId}")
    public ResponseEntity<Car> assignCarToCategory(@PathVariable Long carId, @PathVariable Long categoryId) {
        Car car = carService.getCarById(carId);
        Category category = categoryService.getCategoryById(categoryId);

        car.setCategory(category);
        car = carService.updateCar(car);
        return ResponseEntity.ok(car);
    }
}
