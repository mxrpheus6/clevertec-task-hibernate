package by.clevertec.service;

import by.clevertec.entity.CarShowroom;
import by.clevertec.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarShowroomService {

    private final HibernateUtil hibernateUtil;

    public CarShowroomService(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    public CarShowroom addCarShowroom(CarShowroom showroom) {
        try (Session session = hibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(showroom);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
            return showroom;
        }
    }

    public List<CarShowroom> getAllCarShowrooms() {
        try (Session session = hibernateUtil.getSession()) {
            var criteriaBuilder = session.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(CarShowroom.class);
            criteriaQuery.from(CarShowroom.class);
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    public CarShowroom getCarShowroomById(Long id) {
        try (Session session = hibernateUtil.getSession()) {
            return session.find(CarShowroom.class, id);
        }
    }

    public CarShowroom updateCarShowroom(CarShowroom showroom) {
        try (Session session = hibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(showroom);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
            return showroom;
        }
    }

    public void deleteCarShowroomById(Long id) {
        try (Session session = hibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                CarShowroom showroom = session.find(CarShowroom.class, id);
                if (showroom != null) {
                    session.remove(showroom);
                }
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }
}
