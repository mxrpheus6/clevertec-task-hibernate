package by.clevertec.service;

import by.clevertec.entity.Car;
import by.clevertec.entity.Client;
import by.clevertec.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClientService {

    private final HibernateUtil hibernateUtil;

    public ClientService(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    public Client addClient(Client client) {
        try (Session session = hibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            client.setRegistrationDate(LocalDate.now());
            try {
                session.persist(client);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
            return client;
        }
    }

    public List<Client> getAllClients() {
        try (Session session = hibernateUtil.getSession()) {
            var criteriaBuilder = session.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(Client.class);
            criteriaQuery.from(Client.class);
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    public Client getClientById(Long id) {
        try (Session session = hibernateUtil.getSession()) {
            return session.find(Client.class, id);
        }
    }

    public Client updateClient(Client client) {
        try (Session session = hibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(client);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
            return client;
        }
    }

    public void deleteClientById(Long id) {
        try (Session session = hibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Client client = session.find(Client.class, id);
                if (client != null) {
                    session.remove(client);
                }
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    public Client buyCar(Client client, Car car) {
        try (Session session = hibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            client.getCars().add(car);
            try {
                session.merge(client);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
            return client;
        }
    }
}
