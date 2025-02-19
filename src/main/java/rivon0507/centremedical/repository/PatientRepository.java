package rivon0507.centremedical.repository;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import rivon0507.centremedical.model.Patient;
import rivon0507.centremedical.util.HibernateUtil;

import java.util.List;

public class PatientRepository extends Repository<Patient> {
    public PatientRepository() {
        super(Patient.class);
    }

    public List<Patient> findByNom(String nom) {
        try (Session session = HibernateUtil.openSession()) {
            HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Patient> cq = cb.createQuery(Patient.class);
            Root<Patient> root = cq.from(Patient.class);
            cq.where(cb.like(root.get("nom"), "%" + nom + "%"));
            return session.createQuery(cq).getResultList();
        }
    }
}
