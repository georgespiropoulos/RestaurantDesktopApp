package db.tech.restaurantdesktopapp;

import com.sun.rowset.CachedRowSetImpl;
import java.sql.*;

public class DBUtils {
    private static String driverClassName = "org.postgresql.Driver";
    private static String url = "jdbc:postgresql://dblabs.it.teithe.gr:5432/it144354";
    private static Connection conn = null;
    private static String username = "it144354";
    private static String passwd = "Technologia_Vaseon_2022";

    public static void dbConnect() throws SQLException, ClassNotFoundException {
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your driver?");
            e.printStackTrace();
            throw e;
        }

        System.out.println("Postgres Driver Registered!");

        try {
            conn = DriverManager.getConnection(url, username, passwd);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console " + e);
            e.printStackTrace();
            throw e;
        }
    }

    public static void dbDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed())
                conn.close();
        } catch (Exception e) {
            throw e;
        }
    }

    public static ResultSet dbExecuteQueryUserList() throws ClassNotFoundException, SQLException {
        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSetImpl crs = null;
        try {
            dbConnect();
            System.out.println("Select statement: users \n");
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery("SELECT id,name,surname from employees");
            crs = new CachedRowSetImpl();
            crs.populate(resultSet);
        } catch (SQLException e) {
            System.out.println("Problem occured at executeQuery operation: " + e);
            throw e;
        } finally {
            if (resultSet != null)
                resultSet.close();
            if (stmt != null)
                stmt.close();
            dbDisconnect();
        }
        return crs;
    }
}
