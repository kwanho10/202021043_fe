package kr.ac.kku.cs.wp.kwanho10.support.sql;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HibernateUtilTest {

    private static SessionFactory sessionFactory;

    @BeforeAll
    static void setUp() {
        // SessionFactory 초기화
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Test
    void testSessionFactory() {
        // SessionFactory가 null이 아니고 올바르게 생성되었는지 확인
        assertNotNull(sessionFactory, "SessionFactory should not be null");
    }

    @Test
    void testSession() {
        // 세션을 열고 제대로 동작하는지 확인
        try (Session session = sessionFactory.openSession()) {
            assertNotNull(session, "Session should not be null");
            assertTrue(session.isConnected(), "Session should be connected to the database");
        } catch (Exception e) {
            fail("Failed to open a session: " + e.getMessage());
        }
    }
    
    
    @Test
    @DisplayName("testGetSession")
    public void testGetSession() {
    	Session session=HibernateUtil.getSessionFactory().openSession();
    	assertNotNull(session);
    	session.close();
    }
    
    
    

    @AfterAll
    static void tearDown() {
        // 테스트 후 Hibernate를 종료
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
