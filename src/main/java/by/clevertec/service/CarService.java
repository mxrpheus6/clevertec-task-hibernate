package by.clevertec.service;

import by.clevertec.entity.Car;
import by.clevertec.entity.CarShowroom;
import by.clevertec.entity.Category;
import by.clevertec.entity.enums.SortingOrder;
import by.clevertec.util.HibernateUtil;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.criteria.JoinType;
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

    public List<Car> getAllCarsGraph(SortingOrder sortingOrder) {
        try (Session session = hibernateUtil.getSession()) {
            var criteriaBuilder = session.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(Car.class);
            var carRoot = criteriaQuery.from(Car.class);

            EntityGraph<?> entityGraph = session.getEntityGraph("Car.details");
            if (sortingOrder != null) {
                if (sortingOrder == SortingOrder.ASC) {
                    criteriaQuery.orderBy(criteriaBuilder.asc(carRoot.get("price")));
                } else {
                    criteriaQuery.orderBy(criteriaBuilder.desc(carRoot.get("price")));
                }
            }

            return session.createQuery(criteriaQuery)
                    .setHint("javax.persistence.loadgraph", entityGraph)
                    .getResultList();
        }
    }

    public List<Car> getAllCarsFetch(SortingOrder sortingOrder) {
        try (Session session = hibernateUtil.getSession()) {
            var criteriaBuilder = session.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(Car.class);
            var carRoot = criteriaQuery.from(Car.class);

            carRoot.fetch("category", JoinType.LEFT);
            carRoot.fetch("showroom", JoinType.LEFT);

            if (sortingOrder != null) {
                if (sortingOrder == SortingOrder.ASC) {
                    criteriaQuery.orderBy(criteriaBuilder.asc(carRoot.get("price")));
                } else {
                    criteriaQuery.orderBy(criteriaBuilder.desc(carRoot.get("price")));
                }
            }

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    public List<Car> getAllCarsWithPagination(int page, int pageSize) {
        try (Session session = hibernateUtil.getSession()) {
            var criteriaBuilder = session.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(Car.class);
            var carRoot = criteriaQuery.from(Car.class);

            var query = session.createQuery(criteriaQuery);
            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);

            return query.getResultList();
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

    public Car assignCarToCategory(Car car, Category category) {
        try (Session session = hibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            car.setCategory(category);
            if (!category.getCars().contains(car)) {
                category.getCars().add(car);
            }
            try {
                session.merge(car);
                session.merge(category);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
            return car;
        }
    }

    public List<Car> getAllCarsWithFilters(String brand,
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
                    predicates.add(
                            criteriaBuilder.equal(
                                    criteriaBuilder.lower(carRoot.get("brand")),
                                    brand.toLowerCase()));
                }

                if (category != null && !category.isEmpty()) {
                    predicates.add(criteriaBuilder.equal(
                                    criteriaBuilder.lower(carRoot.get("category")),
                                    category.toLowerCase()));
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
