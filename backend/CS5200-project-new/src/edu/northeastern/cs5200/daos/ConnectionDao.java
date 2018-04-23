package edu.northeastern.cs5200.daos;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionDao {
	private static Connection conn = null;
	public static final String jdbc_drvr = "com.mysql.jdbc.Driver";
	public static final String url = "jdbc:mysql://cs5200-spring2018-chandrashekar.cuznjox4p3cg.us-east-2.rds.amazonaws.com/dbms_project?autoReconnect=true&useSSL=false";
	public static final String user_name = "sangeethac";
	public static final String psswd = "sangeetha";
	
	public static Connection getConnection() {
        return conn;
    }

	public static void estConn(String path) throws IOException, SQLException, ClassNotFoundException {
		Class.forName(ConnectionDao.jdbc_drvr);
		conn = DriverManager.getConnection(ConnectionDao.url, ConnectionDao.user_name, ConnectionDao.psswd);
		conn.close();
	}
}
