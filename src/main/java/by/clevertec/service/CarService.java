package by.clevertec.service;

import by.clevertec.entity.Car;
import by.clevertec.entity.CarShowroom;
import by.clevertec.util.HibernateUtil;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {

    private final HibernateUtil hibernateUtil;

    public CarService(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    public Car addCar(Car car) {
        try (Session session = hibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(car);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
            return car;
        }
    }

    public List<Car> getAllCars() {
        try (Session session = hibernateUtil.getSession()) {
            var criteriaBuilder = session.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(Car.class);
            criteriaQuery.from(Car.class);
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    public Car getCarById(Long id) {
        try (Session session = hibernateUtil.getSession()) {
            return session.find(Car.class, id);
        }
    }

    public Car updateCar(Car car) {
        try (Session session = hibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(car);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
            return car;
        }
    }

    public void deleteCarById(Long id) {
        try (Session session = hibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Car car = session.find(Car.class, id);
                if (car != null) {
                    session.remove(car);
                }
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    public Car assignCarToShowroom(Car car, CarShowroom showroom) {
        try (Session session = hibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            car.setShowroom(showroom);
            if (!showroom.getCars().contains(car)) {
                showroom.getCars().add(car);
            }
            try {
                session.merge(car);
                session.merge(showroom);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
            return car;
        }
    }

    List<Car> findCarsByFilters(String brand,
                                String category,
                                Integer manufactureYear, Double minPrice,
                                Double maxPrice) {
        try (Session session = hibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                var criteriaBuilder = session.getCriteriaBuilder();
                var criteriaQuery = criteriaBuilder.createQuery(Car.class);
                var carRoot = criteriaQuery.from(Car.class);

                List<Predicate> predicates = new ArrayList<>();

                if (brand != null && !brand.isEmpty()) {
                    predicates.add(criteriaBuilder.equal(carRoot.get("brand"), brand));
                }

                if (category != null && !category.isEmpty()) {
                    predicates.add(criteriaBuilder.equal(carRoot.get("category"), category));
                }

                if (manufactureYear != null) {
                    predicates.add(criteriaBuilder.equal(carRoot.get("manufactureYear"), manufactureYear));
                }

                if (minPrice != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(carRoot.get("price"), minPrice));
                }

                if (maxPrice != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(carRoot.get("price"), maxPrice));
                }

                criteriaQuery.select(carRoot).where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
                List<Car> resultList = session.createQuery(criteriaQuery).getResultList();

                transaction.commit();
                return resultList;
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }
}
