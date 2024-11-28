package by.clevertec.service;

import by.clevertec.entity.Car;
import by.clevertec.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

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
}
