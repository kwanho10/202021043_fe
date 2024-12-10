package kr.ac.kku.cs.wp.kwanho10.support.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect  // AOP (Aspect-Oriented Programming) 기능을 활성화하는 어노테이션
@Component  // 스프링 빈으로 등록하기 위한 어노테이션
public class LogginAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());  // 로거 객체 생성

    // @Controller로 애너테이션이 붙은 클래스의 모든 메서드를 매칭하는 포인트컷 정의
    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void callMethods() {
    	System.out.println("callMethods111");
    }

    // 메서드 호출 전 실행되는 어드바이스
    @Before("callMethods()")  // callMethods() 포인트컷이 적용된 메서드 호출 전에 실행
    public void logBefore(JoinPoint joinPoint) {
        // 호출된 메서드의 클래스명과 메서드명 로그로 출력
        logger.trace("before {}#{} ", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName());
    }

    // 메서드 호출 후 실행되는 어드바이스
    @After("callMethods()")  // callMethods() 포인트컷이 적용된 메서드 호출 후에 실행
    public void logAfter(JoinPoint joinPoint) {
        // 호출된 메서드의 클래스명과 메서드명 로그로 출력
        logger.trace("after {}#{} ", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName());
    }

    // 메서드가 정상적으로 실행 완료 후 실행되는 어드바이스
    @AfterReturning(pointcut = "callMethods()", returning = "result")  // 정상적으로 리턴된 경우
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        // 호출된 메서드의 클래스명, 메서드명, 리턴값을 로그로 출력
        logger.trace("after returning {}#{} result {} ",
                joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), result);
    }

    // 메서드 실행 중 예외가 발생했을 때 실행되는 어드바이스
    @AfterThrowing(pointcut = "callMethods()", throwing = "error")  // 예외가 던져진 경우
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        // 호출된 메서드의 클래스명, 메서드명, 예외를 로그로 출력
        logger.trace("after throwing {}#{} Exception {} ",joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), error);
    }
    
    
}
