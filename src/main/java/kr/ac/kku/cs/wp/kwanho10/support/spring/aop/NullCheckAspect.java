package kr.ac.kku.cs.wp.kwanho10.support.spring.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import kr.ac.kku.cs.wp.kwanho10.tools.message.MessageException;

@Aspect
@Component
public class NullCheckAspect {

    private static final Logger logger = LoggerFactory.getLogger(NullCheckAspect.class);

    // @Service 애노테이션이 붙은 클래스의 모든 메서드를 대상으로 하는 포인트컷 정의
    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void callMethods() {
    }

    // 반환된 값이 null인지 확인하는 AfterReturning 어드바이스
    @AfterReturning(pointcut = "callMethods()", returning = "result")
    public void nullCheckAfterReturning(JoinPoint joinPoint, Object result) throws NoSuchMethodException {
        // JoinPoint에서 호출된 메서드 정보를 가져옴
        Method method = getMethodFromJoinPoint(joinPoint);

        // 메서드의 반환 타입을 디버그 로그로 출력
        logger.debug("메서드 반환 타입: {}", method.getReturnType());

        // 반환 타입이 void가 아니고, 반환값(result)이 null인 경우 처리
//        if (!method.getReturnType().equals(Void.TYPE) && result == null) {
//            String message = "";
//
//            logger.trace("null 확인이 호출됨: {}", method.getName());
//
//            // 특정 메서드 이름에 따라 예외 메시지 설정
//            if (joinPoint.getSignature().getName().equals("getUserById")) {
//                message = "User not found: " + joinPoint.getArgs()[0]; // 인자로 전달된 userId를 포함한 메시지 생성
//                logger.debug("생성된 예외 메시지: {}", message);
//            }
//
//            // 커스텀 예외를 발생시킴
//            throw new MessageException(message);
//        }
    }

    // JoinPoint에서 호출된 메서드 객체를 가져오는 헬퍼 메서드
    private Method getMethodFromJoinPoint(JoinPoint joinPoint) throws NoSuchMethodException {
        // 호출된 메서드 이름 가져오기
        String methodName = joinPoint.getSignature().getName();
        // 호출된 대상 클래스 가져오기
        Class<?> targetClass = joinPoint.getTarget().getClass();
        // 메서드의 파라미터 타입 배열 가져오기
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        // 리플렉션을 사용해 메서드 객체 반환
        return targetClass.getMethod(methodName, parameterTypes);
    }
    
    
    
}
