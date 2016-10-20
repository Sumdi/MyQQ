package cn.edu.uestc.MyQQ;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * 
 * establish connection between java files and MySQL
 * 暂时是读取文字信息，以后再处理其他的对象（╯‵□′）╯︵┴─┴ 
 * @author mohanyi
 *
 */

public class MySQLConnect {
	// JDBC 驱动名及数据库 URL
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private static final String DB_URL = "jdbc:mysql://localhost:3306/myqq?useUnicode=true&characterEncoding=utf-8&useSSL=false";
		
	// 数据库的用户名与密码，需要根据自己的设置
	private static final String USER = "root";
	private static final String PASS = "2521";	// 根据本地环境配置
	
	private static Connection conn = null;
	private static Statement stmt = null;
	private static int user_id = 0;
	private static String current_user = "test"; // 先写死用户，以后再说ε=ε=ε=ε=ε=ε=┌(;￣◇￣)┘
	private static ResultSet rs = null;
	
	public static void getConnected(){
		
		try {
			// 注册 JDBC 驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 打开链接
			System.out.println("连接数据库...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			// 创建用于执行静态sql语句的Statement对象
			stmt = conn.createStatement();
			String sql1 = "SELECT id FROM users WHERE username = '" + current_user + "'";
			ResultSet rs = stmt.executeQuery(sql1);
			while(rs.next()){
				user_id = rs.getInt("id");
			}
			System.out.println(user_id);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void storeMessage(String message){
		try {
			getConnected();
			// 执行插入
			System.out.println("插入数据到数据库...");
			String sql = "INSERT INTO messages (content, user_id) VALUES ('" + message + "', '" + user_id + "')";
			stmt.executeUpdate(sql);
			System.out.println("插入数据库成功...");	
			// 完成后关闭
//			rs.close();
			stmt.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			try {
				if(stmt != null) stmt.close();
			} catch(SQLException se) {
			}// 什么都不做
			try {
				if(conn != null) conn.close();
			} catch(SQLException se) {
				se.printStackTrace();
			}
		}
		System.out.println("Goodbye!");
	}
	
	public static String getMessage() {
		String msg = null;
		String time = null;
		try {
			getConnected();
			System.out.println("从数据库中读取消息...");
			String sql  = "SELECT timestamp, content FROM messages WHERE user_id = " + user_id;
			rs = stmt.executeQuery(sql);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT-5:00"));
			rs.last();
			msg = rs.getString("content");
			Timestamp timestamp = rs.getTimestamp("timestamp");
			time = sdf.format(timestamp);
			System.out.println(msg);
			System.out.println(time);
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭资源
			try {
				if(stmt != null) stmt.close();
			} catch(SQLException se) {
			}// 什么都不做
			try {
				if(conn != null) conn.close();
			} catch(SQLException se) {
				se.printStackTrace();
			}
		}
		return current_user + ": " + msg + '\t' + time;
	}
	
	public static void main(String[] args) {
		storeMessage("test message2!!!");
		getMessage();
	}
}
