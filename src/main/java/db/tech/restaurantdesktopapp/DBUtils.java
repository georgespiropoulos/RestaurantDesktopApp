package db.tech.restaurantdesktopapp;

import java.sql.*;

public class DBUtils {
    private static String driverClassName = "org.postgresql.Driver";
    private static String url = "jdbc:postgresql://dblabs.it.teithe.gr:5432/it144354";
    private static Connection conn = null;
    private static String username = "it144354";
    private static String passwd = "Technologia_Vaseon_2022";

    private static Statement statement = null;

    private static ResultSet rs;

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

    public static String checkPass(String username) throws Exception{
        try{
            dbConnect();
            String pass = null;
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT CHECKPASS('"+username+"') as pass");
            while (rs.next()){
                pass = rs.getString("pass");
            }
            dbDisconnect();
            return pass;
        }catch (Exception e){
            throw e;
        }
    }

    public static boolean checkAdmin(String username) throws Exception{
        try{
            dbConnect();
            Boolean admin = false;
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT CHECKADMIN('"+username+"') as administrator");
            while (rs.next()){
                admin = rs.getBoolean("administrator");
            }
            dbDisconnect();
            return admin;
        }catch (Exception e){
            throw e;
        }
    }
}
