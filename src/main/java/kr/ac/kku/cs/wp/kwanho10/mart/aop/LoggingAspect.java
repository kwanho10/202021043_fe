package kr.ac.kku.cs.wp.kwanho10.mart.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @After("execution(* com.mart.product.service.ProductService.updateProduct(..))")
    public void logProductUpdate() {
        logger.info("Product updated successfully.");
    }
    
    
    
    
}