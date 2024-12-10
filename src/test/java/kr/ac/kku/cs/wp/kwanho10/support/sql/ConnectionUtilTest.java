package kr.ac.kku.cs.wp.kwanho10.support.sql;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import kr.ac.kku.cs.wp.kwanho10.support.sql.ConnectionUtil;

public class ConnectionUtilTest {

	@Test
	void testConnection() throws SQLException {
		try (Connection conn = ConnectionUtil.getConnection()) {			
			conn.commit();
		} catch (Exception e) {
			throw e;
		}
	}
}
