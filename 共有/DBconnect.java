package jp.ac.hsc.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnect {
	private static final String W001 = "接続失敗";
	static Connection getConnection(){
		 // DB場所、ユーザ名、パスワード設定
		 String url		= "jdbc:ucanaccess://D:/Users/5192006/Desktop/ec-workspace/MyAAndSManagementSystem/Database.accdb";
		 String user	= "";
		 String pass	= "";

		 Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			System.out.println(W001);
		}	// DB接続
		 return con;
	 }
}
