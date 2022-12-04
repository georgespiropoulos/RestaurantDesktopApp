package db.tech.restaurantdesktopapp;

public class Order {
    private int orderid;
    private int tableid;
    private float bill;

    private int status;

    private Dish[] dishes;

    private Drink[] drinks;

    public Dish[] getDishes() {
        return dishes;
    }

    public void setDishes(Dish[] dishes) {
        this.dishes = dishes;
    }

    public Drink[] getDrinks() {
        return drinks;
    }

    public void setDrinks(Drink[] drinks) {
        this.drinks = drinks;
    }

    public Order() {
    }

    public Order(int orderid, int tableid, int status) {
        this.orderid = orderid;
        this.tableid = tableid;
        this.status = status;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getTableid() {
        return tableid;
    }

    public void setTableid(int tableid) {
        this.tableid = tableid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getBill() {
        calculateBill();
        return bill;
    }

    public void calculateBill(){
        bill = 0;
        for (int i = 0; i < drinks.length; i++){
            try {
                int count = 0;
                count = DBUtils.timesBoughtDrink(drinks[i].getDrinkid(), orderid);
                bill+=drinks[i].getPrice()*count;
            }catch (Exception e){}
        }
        for (int i = 0; i < dishes.length; i++){
            try {
                int count = 0;
                count = DBUtils.timesBoughtDish(dishes[i].getDishid(), orderid);
                bill+=dishes[i].getPrice()*count;
            }catch (Exception e){}
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderid=" + orderid +
                ", tableid=" + tableid +
                ", bill=" + bill +
                '}';
    }
}
