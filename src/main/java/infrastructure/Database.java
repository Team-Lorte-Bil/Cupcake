package infrastructure;

import java.sql.*;
import java.util.TimeZone;

public class Database {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/Cupcake?serverTimezone=" + TimeZone.getDefault().getID();
    
    // Database credentials
    static final String USER = "cupcake";
    static final String PASS = "lortebil";
    
    // Database version
    private static final int version = 1;
    
    public Database() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        
        if (getCurrentVersion() != getVersion()) {
           throw new IllegalStateException("Database in wrong state, expected:"
                   + getVersion() + ", got: " + getCurrentVersion());
       }
    }
    
    public int getCurrentVersion() {
        try (Connection conn = getConnection()) {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("SELECT value FROM properties WHERE name = 'version';");
            if(rs.next()) {
                String column = rs.getString("value");
                return Integer.parseInt(column);
            } else {
                System.err.println("No version in properties.");
                return -1;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
        
    }
    
    public static int getVersion() {
        return version;
    }
    
    
}