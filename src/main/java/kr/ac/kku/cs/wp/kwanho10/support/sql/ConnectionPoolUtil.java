package kr.ac.kku.cs.wp.kwanho10.support.sql;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionPoolUtil {

    private static HikariDataSource dataSource;
    // 풀 상태 확인을 위해 logger 선언 추가
    private static final Logger logger = LogManager.getLogger(ConnectionPoolUtil.class);

    static {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://localhost:3306/kwanho10?serverTimezone=UTC");
            config.setUsername("kwanho10");
            config.setPassword("kwanho10");
            config.setAutoCommit(false); // autocommit 비활성화

            // 커넥션 풀 설정
            config.setMinimumIdle(3); // 최소 3개의 유휴 커넥션 유지
            config.setMaximumPoolSize(10); // 최대 커넥션 풀 사이즈 설정
            config.setConnectionTimeout(30000); // 커넥션 대기 시간 (30초)
            config.setIdleTimeout(600000); // 10분 후 유휴 커넥션 종료
            config.setMaxLifetime(1800000); // 30분 후 커넥션 재생성

            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

 

    // 커넥션 풀에서 커넥션 얻기
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // 커넥션 풀 상태 확인 (모니터링)
    public static void printConnectionPoolStatus() {
    	 logger.debug("Total connections: " + dataSource.getHikariPoolMXBean().getTotalConnections());
         logger.debug("Active connections: " + dataSource.getHikariPoolMXBean().getActiveConnections());
         logger.debug("Idle connections: " + dataSource.getHikariPoolMXBean().getIdleConnections());
    }

    // 데이터 소스 종료
    public static void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();          
        } 
    }
    
    
}
