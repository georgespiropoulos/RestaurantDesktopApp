package db.tech.restaurantdesktopapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
            System.out.println("checkPass completed");
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
            System.out.println("checkAdmin completed");
            return admin;
        }catch (Exception e){
            dbDisconnect();
            throw e;
        }
    }

    public static User getUserDetails(String username) throws Exception{
        try{
            dbConnect();
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM USERDETAILS('"+username+"')");
            while(rs.next()) {
                int id = rs.getInt("eid");
                String name = rs.getString("ename");
                String surname = rs.getString("esurname");
                Boolean isAdmin = rs.getBoolean("eadmin");
                String uname = rs.getString("euser");
                String pass = rs.getString("epass");
                User user = new User(id, name, surname, uname, pass, isAdmin);
                return user;
            }
            dbDisconnect();
        }catch (Exception e){
            System.out.println(e);
            dbDisconnect();
            throw e;
        }
        return null;
    }
}
