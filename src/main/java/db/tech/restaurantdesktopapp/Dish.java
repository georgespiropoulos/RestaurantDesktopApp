package db.tech.restaurantdesktopapp;

public class Dish {
    private int dishid;
    private String dishname;
    private float price;
    private String dishdescription;
    private boolean dishavailability;

    public Dish() {
    }

    public Dish(int dishid, String dishname, float price, String dishdescription, boolean dishavailability) {
        this.dishid = dishid;
        this.dishname = dishname;
        this.price = price;
        this.dishdescription = dishdescription;
        this.dishavailability = dishavailability;
    }

    public int getDishid() {
        return dishid;
    }

    public void setDishid(int dishid) {
        this.dishid = dishid;
    }

    public String getDishname() {
        return dishname;
    }

    public void setDishname(String dishname) {
        this.dishname = dishname;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDishdescription() {
        return dishdescription;
    }

    public void setDishdescription(String dishdescription) {
        this.dishdescription = dishdescription;
    }

    public boolean isDishavailability() {
        return dishavailability;
    }

    public void setDishavailability(boolean dishavailability) {
        this.dishavailability = dishavailability;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "dishid=" + dishid +
                ", dishname='" + dishname + '\'' +
                ", price=" + price +
                ", dishdescription='" + dishdescription + '\'' +
                ", dishavailability=" + dishavailability +
                '}';
    }
}
