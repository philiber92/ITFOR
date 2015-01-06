import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	private static final String HOST = "G:\\Datenbank\\ITFOR";
	private static final String USER = "SA";
	private static final String PASSWORD = "";

	private Connection _conn;

	public Database(String host, String username, String password) {
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
		} catch (ClassNotFoundException e1) {
			System.err.println("Couldn't load driver!");
			e1.printStackTrace();
			System.exit(1);
		}

		try {
			_conn = DriverManager.getConnection("jdbc:hsqldb" + host, username,
					password);
		} catch (SQLException e) {
			System.err.println("Couldn't connect to hsqldb!");
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void query(String statement) {
		Statement stmt;
		try {
			stmt = (Statement) _conn.createStatement();
			stmt.executeQuery(statement);
		} catch (SQLException e) {
			System.err.println("Couldn't execute query!");
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			_conn.close();
		} catch (SQLException e) {
			System.err.println("Couldn't close connection!");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		if (args.length < 1)
			return;

		Database db = new Database(HOST, USER, PASSWORD);

		try(BufferedReader reader 
				= new BufferedReader(new FileReader(args[0].trim()))) {
			
			String stmt = null;

			while ((stmt = reader.readLine()) != null)
				db.query(stmt);
			
		} catch (IOException e) {
			System.err.println("Couldn't read sql file!");
			e.printStackTrace();
			System.exit(1);
		} 
		
		db.close();
	}

}
