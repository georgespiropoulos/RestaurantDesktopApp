package db.tech.restaurantdesktopapp;

public class User {
    private int id;
    private String username;
    private String name;

    private String surname;
    private String pass;

    private Boolean isAdmin;

    public User(){}
    public User(int id, String name,String surname, String un, String pass, Boolean isAdmin){
        this.id = id;
        this.username = un;
        this.name = name;
        this.surname = surname;
        this.pass = pass;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", pass='" + pass + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }


    public User getDetails(String username){
        try {
            User user = new User();
            user = DBUtils.getUserDetails(username);
            return user;
        }catch (Exception e) {

        }
        return null;
    }
}
