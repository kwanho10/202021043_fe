package kr.ac.kku.cs.wp.kwanho10.support.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import kr.ac.kku.cs.wp.kwanho10.support.sql.ConnectionPoolUtil;


public class ConnectionPoolUtilTest {

    @Test
    void testConnection() {
        try (Connection connection = ConnectionPoolUtil.getConnection()) {
            // 커넥션 풀 상태 출력
            ConnectionPoolUtil.printConnectionPoolStatus();

            // SQL 실행
            String query = "SELECT * FROM user";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            // 결과 출력
            while (rs.next()) {
                System.out.println("User: " + rs.getString("name"));
            }

            // 커넥션 풀 상태 출력
            ConnectionPoolUtil.printConnectionPoolStatus();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 커넥션 풀 상태 출력
            ConnectionPoolUtil.printConnectionPoolStatus();
            // 애플리케이션 종료 시 커넥션 풀 종료
            ConnectionPoolUtil.closeDataSource();
        }
    }
}
