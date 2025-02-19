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

    public void save(T patient) {
        try (Session session = HibernateUtil.openSession()) {
            session.beginTransaction();
            session.persist(patient);
            session.getTransaction().commit();
        }
    }
    
    public void update(T patient) {
        try (Session session = HibernateUtil.openSession()) {
            session.beginTransaction();
            session.merge(patient);
            session.getTransaction().commit();
        }
    }
    
    public void delete(T patient) {
        try (Session session = HibernateUtil.openSession()) {
            session.beginTransaction();
            session.remove(patient);
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
