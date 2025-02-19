package rivon0507.centremedical.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import rivon0507.centremedical.model.Medecin;
import rivon0507.centremedical.model.Patient;
import rivon0507.centremedical.model.Visiter;

/// A utility class that manages the hibernate session factory.
/// The {@code boot()} method should be called before any session is opened, and the {@code shutdown()} before stopping
/// the app.
public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private final static Class<?>[] ENTITIES = new Class[]{
            // Add the entity classes here
            Patient.class,
            Medecin.class,
            Visiter.class,
    };

    /// Creates and initializes the session factory if it is closed or null
    public static void boot() {
        if (isUp()) return;

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().build();
        try {
            sessionFactory = new MetadataSources(registry)
                    .addAnnotatedClasses(ENTITIES)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw new RuntimeException(e);
        }
    }

    /// Opens a session from the session factory.
    ///
    /// @return the opened session
    /// @throws IllegalStateException if called before boot
    public static Session openSession() {
        if (!isUp())
            throw new IllegalStateException("Utility class is not booted");
        return sessionFactory.openSession();
    }

    /// Closes the session factory
    public static void shutdown() {
        if (isUp()) {
            sessionFactory.close();
        }
        sessionFactory = null;
    }

    /// Queries the state of the factory
    public static boolean isUp() {
        return sessionFactory != null && sessionFactory.isOpen();
    }
}
