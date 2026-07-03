
package CampusBite;

public class Item {
    private String name;
    private int price;
    private int availableQuantity;
    
    public Item(String name, int price, int availableQuantity) {
        this.name = name;
        this.price = price;
        this.availableQuantity = availableQuantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
    
    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void reduceQuantity( int quantity ) {
        availableQuantity -= quantity;
    }
    
    public String toString() {
        return name + " - " + price + " SAR - Available: " + availableQuantity;
    }
    
}
