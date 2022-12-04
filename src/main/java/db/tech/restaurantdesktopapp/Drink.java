package db.tech.restaurantdesktopapp;

public class Drink {
    private int drinkid;
    private String drinkname;
    private float price;
    private String drinkdescription;
    private boolean drinkavailability;

    public Drink() {
    }

    public Drink(int drinkid, String drinkname, float price, String drinkdescription, boolean drinkavailability) {
        this.drinkid = drinkid;
        this.drinkname = drinkname;
        this.price = price;
        this.drinkdescription = drinkdescription;
        this.drinkavailability = drinkavailability;
    }

    public int getDrinkid() {
        return drinkid;
    }

    public void setDrinkid(int drinkid) {
        this.drinkid = drinkid;
    }

    public String getDrinkname() {
        return drinkname;
    }

    public void setDrinkname(String drinkname) {
        this.drinkname = drinkname;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDrinkdescription() {
        return drinkdescription;
    }

    public void setDrinkdescription(String drinkdescription) {
        this.drinkdescription = drinkdescription;
    }

    public boolean isDrinkavailability() {
        return drinkavailability;
    }

    public void setDrinkavailability(boolean drinkavailability) {
        this.drinkavailability = drinkavailability;
    }

    @Override
    public String toString() {
        return "Drink{" +
                "drinkid=" + drinkid +
                ", drinkname='" + drinkname + '\'' +
                ", price=" + price +
                ", drinkdescription='" + drinkdescription + '\'' +
                ", drinkavailability=" + drinkavailability +
                '}';
    }
}
