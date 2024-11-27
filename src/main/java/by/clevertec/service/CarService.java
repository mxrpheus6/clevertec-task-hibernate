package by.clevertec.service;

import by.clevertec.entity.Car;
import by.clevertec.util.HibernateUtil;
import lombok.val;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CarService {
    public Car addCar(Car car) {
        try (Session session = HibernateUtil.getSession()) {
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
        try (Session session = HibernateUtil.getSession()) {
            var criteriaBuilder = session.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(Car.class);
            criteriaQuery.from(Car.class);
            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}
