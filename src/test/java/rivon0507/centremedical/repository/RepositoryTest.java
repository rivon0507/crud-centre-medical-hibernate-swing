package rivon0507.centremedical.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import rivon0507.centremedical.model.Medecin;
import rivon0507.centremedical.util.HibernateUtil;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class RepositoryTest {
    MockedStatic<HibernateUtil> mockHibernateUtil;
    @Mock Session mockSession;
    @Mock private Transaction mockTransaction;
    AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        mockHibernateUtil = Mockito.mockStatic(HibernateUtil.class);
    }

    @AfterEach
    void tearDown() throws Exception {
        mockHibernateUtil.close();
        mocks.close();
    }

    @Test
    void save() {
        doNothing().when(mockTransaction).commit();
        doNothing().when(mockSession).persist(any());
        given(mockSession.getTransaction()).willReturn(mockTransaction);
        mockHibernateUtil.when(HibernateUtil::openSession)
                .thenReturn(mockSession);

        Medecin medecin = new Medecin();
        medecin.setId(1L);
        new MedecinRepository().save(medecin);

        verify(mockSession, times(1)).persist(medecin);
    }

    @Test
    void update() {
        doNothing().when(mockTransaction).commit();
        given(mockSession.merge(any())).willReturn(new Medecin());
        given(mockSession.getTransaction()).willReturn(mockTransaction);
        mockHibernateUtil.when(HibernateUtil::openSession)
                .thenReturn(mockSession);

        Medecin medecin = new Medecin();
        medecin.setId(1L);
        new MedecinRepository().update(medecin);

        verify(mockSession, times(1)).merge(medecin);
    }

    @Test
    void delete() {
        doNothing().when(mockTransaction).commit();
        doNothing().when(mockSession).remove(any());
        given(mockSession.getTransaction()).willReturn(mockTransaction);
        mockHibernateUtil.when(HibernateUtil::openSession)
                .thenReturn(mockSession);

        Medecin medecin = new Medecin();
        medecin.setId(1L);
        new MedecinRepository().delete(medecin);

        verify(mockSession, times(1)).remove(medecin);
    }

    @SuppressWarnings("unchecked")
    @Test
    void findById() {
        given(mockSession.get(any(Class.class), anyLong())).willReturn(new Medecin());
        given(mockSession.getTransaction()).willReturn(mockTransaction);
        mockHibernateUtil.when(HibernateUtil::openSession)
                .thenReturn(mockSession);

        new MedecinRepository().findById(1L);

        verify(mockSession, times(1)).get(Medecin.class, 1L);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    void findAll() {
        Query query = mock(Query.class);
        given(query.list()).willReturn(List.of());
        given(mockSession.createQuery(any(), any())).willReturn(query);
        given(mockSession.getTransaction()).willReturn(mockTransaction);
        mockHibernateUtil.when(HibernateUtil::openSession)
                .thenReturn(mockSession);

        new MedecinRepository().findAll();

        verify(mockSession, times(1)).createQuery(any(), any());
    }
}