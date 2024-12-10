package kr.ac.kku.cs.wp.kwanho10.support.sql;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.Entity;

import org.reflections.Reflections;  // Reflections 라이브러리 임포트

import java.util.Set;
import java.util.stream.Collectors;

public class HibernateUtil {

    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);  // Logger 생성

    // SessionFactory 객체 생성
    private static final SessionFactory sessionFactory = buildSessionFactory();

    // SessionFactory를 반환하는 메서드
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // 애플리케이션 종료 시 세션 팩토리 종료
    public static void shutdown() {
        getSessionFactory().close();
    }

    // SessionFactory를 생성하는 메서드
    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration().configure();  // Hibernate 설정을 로드
            String packageName = configuration.getProperty("entity.package.scan");  // 엔티티 패키지 이름 가져오기
            logger.debug("entity.package.scan: {}", packageName);  // 패키지 이름 로그 출력
            if (packageName != null && !packageName.isBlank()) {  // 패키지 이름이 유효하면
                Set<Class<?>> entityClasses = findEntityClasses(packageName);  // 해당 패키지 내의 엔티티 클래스 찾기
                for (Class<?> entityClass : entityClasses) {
                    logger.debug(entityClass.getName());  // 엔티티 클래스 이름 로그 출력
                    configuration.addAnnotatedClass(entityClass);  // 엔티티 클래스를 Hibernate 설정에 추가
                }
            }
            return configuration.buildSessionFactory();  // SessionFactory 생성 및 반환
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);  // 초기화 중 예외가 발생하면 예외 처리
        }
    }

    // 주어진 패키지 이름에서 엔티티 클래스를 찾는 메서드
    private static Set<Class<?>> findEntityClasses(String packageName) {
        Set<Class<?>> rtn = null;
        try {
            // Reflections 라이브러리를 사용하여 패키지 내 엔티티 클래스들을 찾음
            Reflections reflections = new Reflections(packageName);  // Reflections 객체 생성
            rtn = reflections.getTypesAnnotatedWith(Entity.class).stream()  // @Entity로 애너테이션된 클래스 찾기
                    .filter(cls -> cls.isAnnotationPresent(Entity.class))  // Entity 애너테이션이 있는 클래스 필터링
                    .collect(Collectors.toSet());  // 결과를 Set으로 반환
        } catch (Exception e) {
            // 예외 발생 시 경고 로그 출력
            logger.warn("No Entity Class: {}", e.getMessage());
        }
        return rtn;  // 엔티티 클래스 집합 반환
    }
}
