package rivon0507.centremedical.util;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HibernateUtilTest {

    @Test
    void boot() {
        HibernateUtil.boot();

        assertDoesNotThrow(
                () -> HibernateUtil.openSession().close(),
                "Boot should initialize a session factory"
        );
        Session session = HibernateUtil.openSession();
        assertTrue(session.getSessionFactory().isOpen(), "The session factory should be open after boot");
        session.close();
        HibernateUtil.shutdown();
    }

    @Test
    void openSession() {
        assertThrows(
                IllegalStateException.class,
                () -> {
                    Session session = HibernateUtil.openSession();
                    session.close();
                },
                "Opening a session before boot should throw an exception"
        );
        assertThrows(
                IllegalStateException.class,
                () -> {
                    HibernateUtil.boot();
                    HibernateUtil.shutdown();
                    Session session = HibernateUtil.openSession();
                    session.close();
                },
                "Opening a session after shutdown should throw an exception"
        );

    }

    @Test
    void shutdown() {
        HibernateUtil.boot();
        HibernateUtil.shutdown();
        assertThrows(
                IllegalStateException.class,
                () -> HibernateUtil.openSession().close(),
                "Should be unable to open a session after shutdown"
        );
    }

    @Test
    void isUp() {
        HibernateUtil.boot();
        assertTrue(HibernateUtil.isUp(), "The session factory should be up after boot");
        HibernateUtil.shutdown();
        assertFalse(HibernateUtil.isUp(), "The session factory should not be up after shutdown");
    }
}