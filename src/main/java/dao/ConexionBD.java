package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.cj.jdbc.MysqlDataSource;

public class ConexionBD {

	private static ConexionBD instance;
	private Connection con;

	private ConexionBD() {
		Properties prop = new Properties();
		try (FileInputStream fis = new FileInputStream("src/main/resources/application.properties")) {
			prop.load(fis);
			MysqlDataSource mSql = new MysqlDataSource();
			mSql.setURL(prop.getProperty("URL"));
			mSql.setUser(prop.getProperty("usuarioadmin"));
			mSql.setPassword(prop.getProperty("claveadmin"));
			this.con = mSql.getConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ConexionBD getInstance() {
		if (instance == null) {
			instance = new ConexionBD();
		}
		return instance;
	}

	public Connection getConnection() {
		return con;
	}
	// comentario
}
