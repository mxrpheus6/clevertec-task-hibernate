package by.clevertec;

import by.clevertec.entity.Car;
import by.clevertec.service.CarService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        /*
        CarService carService = new CarService();

        Car car = new Car();
        car.setBrand("Volkswagen");
        car.setModel("Golf 5");
        car.setPrice(6000.0);
        car.setManufactureYear(2005);

        carService.addCar(car);
        List<Car> cars = carService.getAllCars();

        System.out.println(cars);
         */
    }
}
