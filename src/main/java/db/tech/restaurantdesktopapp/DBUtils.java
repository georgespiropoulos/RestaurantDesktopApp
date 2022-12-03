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
            String pass = null;
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT TBD.CHECKPASS('"+username+"') as pass");
            while (rs.next()){
                pass = rs.getString("pass");
            }
            System.out.println("checkPass completed");
            return pass;
        }catch (Exception e){
            throw e;
        }
    }

    public static boolean checkAdmin(String username) throws Exception{
        try{
            Boolean admin = false;
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT TBD.CHECKADMIN('"+username+"') as administrator");
            while (rs.next()){
                admin = rs.getBoolean("administrator");
            }
            System.out.println("checkAdmin completed");
            return admin;
        }catch (Exception e){
            throw e;
        }
    }

    public static User getUserDetails(String username) throws Exception{
        try{
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM TBD.USERDETAILS('"+username+"')");
            while(rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String surname = rs.getString(3);
                Boolean isAdmin = rs.getBoolean(4);
                String uname = rs.getString(5);
                String pass = rs.getString(6);
                User user = new User(id, name, surname, uname, pass, isAdmin);
                return user;
            }
        }catch (Exception e){
            System.out.println(e);
            throw e;
        }
        return null;
    }

    public static User updateUser(String name, String surname, String uname, String pass, int id) throws Exception{
        try{
            statement = conn.createStatement();
            String stmt = String.format("'%s','%s','%s','%s',%d",name,surname,uname,pass,id);
            rs = statement.executeQuery("SELECT * FROM TBD.USERUPDATE("+stmt+")");
            while (rs.next()){
                System.out.println(rs.getString(1));
            }
        }catch (Exception e){
            System.out.println(e);
            throw e;
        }
        return null;
    }
}
