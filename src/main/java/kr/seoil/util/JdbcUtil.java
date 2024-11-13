package kr.seoil.util;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JdbcUtil {
	public Connection connect() {
		Connection conn = null;
		try {
			Context initCTX = new InitialContext();
		    DataSource ds = (DataSource) initCTX.lookup("java:comp/env/jdbc/seoil");
		    conn = ds.getConnection();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public void close(Connection conn, PreparedStatement ps, ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		close(conn,ps);
	}
	
	public void close(Connection conn, PreparedStatement ps) {
		if(ps !=null) {
			try {
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(conn !=null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
