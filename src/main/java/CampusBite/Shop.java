
package CampusBite;
import java.util.ArrayList;

public class Shop {
    private String name;
    private ArrayList<Item> items;

    public Shop( String name ) {
        this.name = name;
        this.items = new ArrayList<>();
    }
    
    public void addItem( Item item ) {
        items.add(item);
    }
    
    public String getName() {
        return name;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void printMenu() {
        System.out.println("Menu for " + name + ":");
        for ( int i = 0; i < items.size(); i++ ) {
            System.out.println( (i + 1) + ". " + items.get(i).toString() );
        } //for
    }
 
}
