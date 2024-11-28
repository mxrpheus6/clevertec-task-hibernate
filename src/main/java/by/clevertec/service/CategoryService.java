package by.clevertec.service;

import by.clevertec.entity.Category;
import by.clevertec.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final HibernateUtil hibernateUtil;

    public CategoryService(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    public Category addCategory(Category category) {
        try (Session session = hibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(category);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
            return category;
        }
    }

    public List<Category> getAllCategories() {
        try (Session session = hibernateUtil.getSession()) {
            var criteriaBuilder = session.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(Category.class);
            criteriaQuery.from(Category.class);
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    public Category getCategoryById(Long id) {
        try (Session session = hibernateUtil.getSession()) {
            return session.find(Category.class, id);
        }
    }

    public Category updateCategory(Category category) {
        try (Session session = hibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(category);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
            return category;
        }
    }

    public void deleteCategoryById(Long id) {
        try (Session session = hibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Category category = session.find(Category.class, id);
                if (category != null) {
                    session.remove(category);
                }
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }
}
