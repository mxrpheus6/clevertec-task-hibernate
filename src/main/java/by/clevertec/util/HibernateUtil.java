package by.clevertec.util;

import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HibernateUtil {

    private final SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.openSession();
    }

    public void shutdown() {
        sessionFactory.close();
    }

    public void dropAllTables() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            String sql = "DROP SCHEMA public CASCADE; CREATE SCHEMA public;";
            Query query = session.createNativeQuery(sql);
            query.executeUpdate();
            transaction.commit();
            System.out.println("All tables dropped successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            shutdown();
        }
    }
}