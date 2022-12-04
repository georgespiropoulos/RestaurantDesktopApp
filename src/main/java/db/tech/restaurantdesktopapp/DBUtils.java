package db.tech.restaurantdesktopapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    public static Order getOrderDetails(int orderid) throws Exception{
        try{
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM TBD.ORDERDETAILS("+orderid+")");
            while(rs.next()) {
                int id = rs.getInt(1);
                int tableid = rs.getInt(2);
                Order order = new Order(id, tableid);
                return order;
            }
        }catch (Exception e){
            System.out.println(e);
            throw e;
        }
        return null;
    }

    public static int timesBoughtDish(int dishid, int orderid) throws Exception{
        try{
            int count = 0;
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT TBD.TIMESBOUGHTDISH("+dishid+","+orderid+")");
            while (rs.next()){
                count = rs.getInt(1);
            }
            return count;
        }catch (Exception e){
            throw e;
        }
    }

    public static int timesBoughtDrink(int drinkid, int orderid) throws Exception{
        try{
            int count = 0;
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT TBD.TIMESBOUGHTDRINK("+drinkid+","+orderid+")");
            while (rs.next()){
                count = rs.getInt(1);
            }
            return count;
        }catch (Exception e){
            throw e;
        }
    }

    public static void updateUser(String name, String surname, String uname, String pass, int id) throws Exception{
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
    }

    public static void updateOrder(int orderid, int tableid, float bill) throws Exception{
        try{
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM TBD.ORDERUPDATE("+orderid+","+tableid+","+bill+")");
            while (rs.next()){
                System.out.println(rs.getString(1));
            }
        }catch (Exception e){
            System.out.println(e);
            throw e;
        }
    }

    public static void updateUserRole(boolean role, int userid) throws Exception{
        try{
            statement = conn.createStatement();
            String stmt = String.format("%b,%d",role,userid);
            rs = statement.executeQuery("SELECT * FROM TBD.USERUPDATEROLE("+stmt+")");
            while (rs.next()){
                System.out.println(rs.getString(1));
            }
        }catch (Exception e){
            System.out.println(e);
            throw e;
        }
    }

    public static void insertUser(String name, String surname, String uname, String pass, boolean admin) throws Exception{
        try{
            statement = conn.createStatement();
            String stmt = String.format("'%s','%s','%s','%s',%b",name,surname,uname,pass,admin);
            rs = statement.executeQuery("SELECT * FROM TBD.USERINSERT("+stmt+")");
            while (rs.next()){
                System.out.println(rs.getString(1));
            }
        }catch (Exception e){
            System.out.println(e);
            throw e;
        }
    }

    public static void deleteEmployee(int id) throws Exception{
        try{
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM TBD.USERDELETE("+id+")");
            while (rs.next()){
                System.out.println(rs.getString(1));
            }
        }catch (Exception e){
            System.out.println(e);
            throw e;
        }
    }

    public static String[] getUsers() throws Exception{
        try{
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM TBD.GETUSERS()");
            int i = 0;
            int size=0;
            while (rs.next()) {
                size++;
            }
            String[] users = new String[size];
            rs = statement.executeQuery("SELECT * FROM TBD.GETUSERS()");
            while (rs.next()){
                users[i] = rs.getString("username");
                i++;
            }
            return users;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public static String[] getOrders() throws Exception{
        try{
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM TBD.GETORDERS()");
            int i = 0;
            int size=0;
            while (rs.next()) {
                size++;
            }
            String[] users = new String[size];
            rs = statement.executeQuery("SELECT * FROM TBD.GETORDERS()");
            while (rs.next()){
                users[i] = rs.getString("orderid");
                i++;
            }
            return users;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public static Drink[] getOrderDrinks(int orderid) throws Exception{
        try{
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM TBD.ORDERDRINKS("+orderid+")");
            int i = 0;
            int size=0;
            while (rs.next()) {
                size++;
            }
            Drink[] drinks = new Drink[size];
            rs = statement.executeQuery("SELECT * FROM TBD.ORDERDRINKS("+orderid+")");
            while(rs.next()) {
                int drinkid = rs.getInt("drinkid");
                String drinkname = rs.getString("drinkname");
                float price = rs.getFloat("price");
                String drinkdescription = rs.getString("drinkdescription");
                Boolean drinkavailability = rs.getBoolean("drinkavailability");
                drinks[i] = new Drink(drinkid, drinkname, price, drinkdescription, drinkavailability);
                i++;
            }
            return drinks;
        }catch (Exception e){
            System.out.println(e);
            throw e;
        }
    }

    public static Dish[] getOrderDishes(int orderid) throws Exception{
        try{
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM TBD.ORDERDISHES("+orderid+")");
            int i = 0;
            int size=0;
            while (rs.next()) {
                size++;
            }
            Dish[] dishes = new Dish[size];
            rs = statement.executeQuery("SELECT * FROM TBD.ORDERDISHES("+orderid+")");
            while(rs.next()) {
                int dishid = rs.getInt("dishid");
                String dishname = rs.getString("dishname");
                float price = rs.getFloat("price");
                String dishdescription = rs.getString("dishdescription");
                Boolean dishavailability = rs.getBoolean("dishavailability");
                dishes[i] = new Dish(dishid, dishname, price, dishdescription, dishavailability);
                i++;
            }
            return dishes;
        }catch (Exception e){
            System.out.println(e);
            throw e;
        }
    }

    public static ObservableList<String> getAvailableDrinks() throws Exception{
        try{
            statement = conn.createStatement();
            ObservableList<String> drinks = FXCollections.observableArrayList();
            rs = statement.executeQuery("SELECT * FROM TBD.GETAVAILABLEDRINKS()");
            while(rs.next()) {
                int drinkid = rs.getInt("drinkid");
                String drinkname = rs.getString("drinkname");
                float price = rs.getFloat("price");
                String line = String.format("%-10.10s \t %-40.40s\t %-4.4s €",drinkid, drinkname, price);
                drinks.add(line);
            }
            return drinks;
        }catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static ObservableList<String> getAvailableDishes() throws Exception{
        try{
            statement = conn.createStatement();
            ObservableList<String> dishes = FXCollections.observableArrayList();
            rs = statement.executeQuery("SELECT * FROM TBD.GETAVAILABLEDISHES()");
            while(rs.next()) {
                int dishid = rs.getInt("dishid");
                String dishname = rs.getString("dishname");
                float price = rs.getFloat("price");
                String line = String.format("%-10.10s \t %-40.40s\t %-4.4s €",dishid, dishname, price);
                dishes.add(line);
            }
            return dishes;
        }catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static Drink getDrink(int drinkid) throws Exception{
        try{
            statement = conn.createStatement();

            Drink drink = new Drink();
            rs = statement.executeQuery("SELECT * FROM TBD.GETDRINK("+drinkid+")");
            while(rs.next()) {
                String drinkname = rs.getString("drinkname");
                float price = rs.getFloat("price");
                String drinkdescription = rs.getString("drinkdescription");
                Boolean drinkavailability = rs.getBoolean("drinkavailability");
                drink = new Drink(drinkid, drinkname, price, drinkdescription, drinkavailability);
            }
            return drink;
        }catch (Exception e){
            System.out.println(e);
            throw e;
        }
    }

    public static void addDrinktoOrder(int drinkid, int orderid) throws Exception{
        try{
            statement = conn.createStatement();
            String stmt = String.format("%d,%d",drinkid,orderid);
            rs = statement.executeQuery("SELECT * FROM TBD.ADDDRINKTOORDER("+stmt+")");
            while (rs.next()){
                System.out.println(rs.getString(1));
            }
        }catch (Exception e){
            System.out.println(e);
            throw e;
        }
    }

    public static void addDishtoOrder(int dishid, int orderid) throws Exception{
        try{
            statement = conn.createStatement();
            String stmt = String.format("%d,%d",dishid,orderid);
            rs = statement.executeQuery("SELECT * FROM TBD.ADDDISHTOORDER("+stmt+")");
            while (rs.next()){
                System.out.println(rs.getString(1));
            }
        }catch (Exception e){
            System.out.println(e);
            throw e;
        }
    }

    public static Dish getDish(int dishid) throws Exception{
        try{
            statement = conn.createStatement();

            Dish dish = new Dish();
            rs = statement.executeQuery("SELECT * FROM TBD.GETDISH("+dishid+")");
            while(rs.next()) {
                String dishname = rs.getString("dishname");
                float price = rs.getFloat("price");
                String dishdescription = rs.getString("dishdescription");
                Boolean dishavailability = rs.getBoolean("dishavailability");
                dish = new Dish(dishid, dishname, price, dishdescription, dishavailability);
            }
            return dish;
        }catch (Exception e){
            System.out.println(e);
            throw e;
        }
    }


}
