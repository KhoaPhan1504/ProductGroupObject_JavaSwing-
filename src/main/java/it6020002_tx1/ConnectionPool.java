package it6020002_tx1;

import java.sql.*;

public interface ConnectionPool {
	// Phuong thuc xin ket noi
	public Connection getConnection(String objectName) throws SQLException;
	
	// Phuong thuc yeu cau tra ve ket qua ket noi
	public void releaseConnection(Connection con, String objectName) throws SQLException;

}
