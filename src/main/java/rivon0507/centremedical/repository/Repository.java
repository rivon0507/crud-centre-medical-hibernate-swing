package rivon0507.centremedical.repository;

import org.hibernate.Session;
import rivon0507.centremedical.util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class Repository<T> {
    private final Class<T> entityClass;

    public Repository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void save(T entity) {
        try (Session session = HibernateUtil.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
        }
    }

    public void update(T entity) {
        try (Session session = HibernateUtil.openSession()) {
            session.beginTransaction();
            session.merge(entity);
            session.getTransaction().commit();
        }
    }

    public void delete(T entity) {
        try (Session session = HibernateUtil.openSession()) {
            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();
        }
    }

    public Optional<T> findById(Long id) {
        try (Session session = HibernateUtil.openSession()) {
            return Optional.ofNullable(session.get(entityClass, id));
        }
    }

    public List<T> findAll() {
        try (Session session = HibernateUtil.openSession()) {
            return session.createQuery("from " + entityClass.getName(), entityClass).list();
        }
    }
}
