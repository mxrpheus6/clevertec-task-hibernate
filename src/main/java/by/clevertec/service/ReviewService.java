package by.clevertec.service;

import by.clevertec.entity.Car;
import by.clevertec.entity.Client;
import by.clevertec.entity.Review;
import by.clevertec.util.HibernateUtil;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    private final HibernateUtil hibernateUtil;

    public ReviewService(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    @Transactional
    public List<Review> searchReviews(String keyword, Integer rating) {
        try (Session session = hibernateUtil.getSession()) {
            var criteriaBuilder = session.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(Review.class);
            var reviewRoot = criteriaQuery.from(Review.class);

            List<Predicate> predicates = new ArrayList<>();

            if (keyword != null && !keyword.isEmpty()) {
                String likePattern = "%" + keyword.toLowerCase() + "%";
                Predicate textLike = criteriaBuilder.like(criteriaBuilder.lower(
                        reviewRoot.get("text")),
                        likePattern);
                predicates.add(textLike);
            }

            if (rating != null) {
                predicates.add(criteriaBuilder.equal(reviewRoot.get("rating"), rating));
            }

            if (!predicates.isEmpty()) {
                criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
            }

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    public Review addReview(Client client, Car car, Review review) {
        try (Session session = hibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            if (!client.getCars().contains(car)) {
                throw new RuntimeException("Chosen car does not belong to client");
            }
            review.setClient(client);
            review.setCar(car);
            try {
                session.persist(review);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
            return review;
        }
    }

    public List<Review> getAllReviews() {
        try (Session session = hibernateUtil.getSession()) {
            var criteriaBuilder = session.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(Review.class);
            criteriaQuery.from(Review.class);
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    public Review getReviewById(Long id) {
        try (Session session = hibernateUtil.getSession()) {
            return session.find(Review.class, id);
        }
    }

    public Review updateReview(Review review) {
        try (Session session = hibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(review);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
            return review;
        }
    }

    public void deleteReviewById(Long id) {
        try (Session session = hibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Review review = session.find(Review.class, id);
                if (review != null) {
                    session.remove(review);
                }
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }
}
