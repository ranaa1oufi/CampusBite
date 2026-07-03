
package CampusBite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Read_And_Write_A_File {
    static ArrayList<Shop> shops = new ArrayList<>();
    static ArrayList<User> users = new ArrayList<>();
    
    
    
    public void loadShopsFromFile() throws FileNotFoundException {
        File file = new File("menus.txt");

        if ( !file.exists() ) {
            System.out.println("File not found");
            return;
        }

        Scanner in = new Scanner(file);
        Shop currentShop = null;

        while ( in.hasNextLine() ) {
            String line = in.nextLine();

            if ( line.equals("") )
                continue;  // Skip empty lines (used to separate shops)

            // If line does NOT start with "-" --> it is a shop name
            if ( !line.startsWith("-") ) {
                // Create new shop and add to list
                currentShop = new Shop(line);
                shops.add(currentShop);
            }

            else { // Its an item
                line = line.substring(2); // Remove "- " from the beginning

                String[] parts = line.split(", ");  // Split into name and price using comma

                String name = parts[0];

                // Remove " SAR" and convert price to integer
                String priceText = parts[1].replace(" SAR", "");
                int price = Integer.parseInt(priceText.trim());

                int quantity = Integer.parseInt(parts[2].trim());

                // Create item and add it to current shop
                Item item = new Item(name, price,quantity);
                currentShop.addItem(item);
            }
        } // while
        in.close();
    } //loadShopsFromFile
    
    
    
    
    
    
    public void loadUsersFromFile() throws FileNotFoundException {
        File file = new File("users.txt");

        if ( !file.exists() ) {
            System.out.println("File not found");
            return;
        }

        Scanner in = new Scanner(file);

        while ( in.hasNextLine() ) {
            String line = in.nextLine();

            if ( line.equals("") )
                continue; // Skip empty lines

            String[] parts = line.split(", ");

            String firstName = parts[0];
            String lastName = parts[1];
            String email = parts[2];
            String password = parts[3];
            String phoneNumber = parts[4];

            User user = new User(firstName, lastName, email, password, phoneNumber);
            users.add(user);
        } // while
        in.close();
    } // loadUsersFromFile
    
    
    
    
    public void saveUserToFile(User user) throws IOException {
        File file = new File("users.txt");

        if ( !file.exists() ) {
            System.out.println("File not found");
            return;
        }

        FileWriter FileWriter = new FileWriter(file, true); // append = true
        BufferedWriter BufferedWriter = new BufferedWriter(FileWriter);

        BufferedWriter.write(user.getFirstName() + ", " +
                 user.getLastName() + ", " +
                 user.getEmail() + ", " +
                 user.getPassword() + ", " +
                 user.getPhoneNumber());

        BufferedWriter.newLine();

        BufferedWriter.close();
    } // saveUserToFile
    
    

}
