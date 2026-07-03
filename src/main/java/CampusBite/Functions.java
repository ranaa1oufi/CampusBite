
package CampusBite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Functions {
    Read_And_Write_A_File Recall = new Read_And_Write_A_File();
    ArrayList<User> users = Read_And_Write_A_File.users;
    ArrayList<Shop> shops = Read_And_Write_A_File.shops;
    static ArrayList<Item> basketItems = new ArrayList<>();
    static ArrayList<Integer> quantities = new ArrayList<>();
    
    
    // main method (creating an account)
    public String CreateAccount() throws IOException {
        Scanner in = new Scanner(System.in);

        while ( true ) {

            System.out.println("Please Enter Your Information for creating an account: ");

            System.out.print("First Name: ");
            String firstName = in.nextLine();

            System.out.print("Last Name: ");
            String lastName = in.nextLine();

            System.out.print("Email(Must contain @): ");
            String email = in.nextLine();

            System.out.print("Password(At least 6 characters): ");
            String password = in.nextLine();

            System.out.print("Phone Number(Must be 10 numbers): ");
            String phoneNumber = in.nextLine();

            User newUser = new User(firstName, lastName, email, password, phoneNumber);

            if ( newUser.getEmail() == null || !newUser.getEmail().contains("@") ) {
                System.out.println("Invalid email");
                continue;
            }

            if ( newUser.getPassword() == null || newUser.getPassword().length() < 6 ) {
                System.out.println("Weak password");
                continue;
            }
            
            if ( newUser.getPhoneNumber() == null || !newUser.getPhoneNumber().matches("\\d{10}") ) {
                System.out.println("Invalid phone number (must be exactly 10 digits)");
                continue;
            }

            boolean exists = false;

            for ( User u : users ) {

                if ( u.getEmail().equals(newUser.getEmail()) || u.getPhoneNumber().equals(newUser.getPhoneNumber()) ) {
                    System.out.println("\nUser already exists");

                    System.out.println("1) Try another phone number");
                    System.out.println("2) Login");

                    int choice;

                    while ( true ) {
                        System.out.print("Enter your choice: ");
                        
                        if ( !in.hasNextInt() ) {
                            System.out.println("Invalid input! Enter a number.\n");
                            in.next(); // remove invalid input
                            continue;
                        }
                        
                        choice = in.nextInt();
                        in.nextLine();

                        if ( choice == 1 ) {
                            exists = true;
                            System.out.println();
                            break;
                        }

                        else if ( choice == 2 ) {
                            System.out.println();
                            return Login();
                        }

                        else {
                            System.out.println("Invalid choice! Try again.");
                        }
                    }
                    break;
                }
            }

            if ( exists ) {
                continue;
            }

            users.add(newUser);

            Recall.saveUserToFile(newUser);

            System.out.println("Account created successfully \n");
            return newUser.getFirstName() + " " + newUser.getLastName();

        } // while true
    } // CreateAccount
    
    
    
    
    
    
    // method to login
    public String Login() throws IOException {
        Scanner in = new Scanner(System.in);
        User foundUser = null;
        System.out.println("Please Enter Your Information for login into account: ");

        System.out.print("Enter Phone Number: ");
        String phoneNumber = in.nextLine();
        
        // Ask for password
        System.out.print("Enter password: ");
        String password = in.nextLine();
    
        // Search for user
        for ( User u : users ) {
            if ( u.getPhoneNumber().equals(phoneNumber) ) {
                foundUser = u;
                break;
            } //if
        } // for

        // If user NOT found
    if ( foundUser == null ) {
        System.out.println("\nUser not found");
        System.out.println("1) Try another phone number");
        System.out.println("2) Create new account");
        System.out.print("Enter your choice: ");
        int choice;
        
        while ( true ) {
             choice = in.nextInt();
             in.nextLine();
             System.out.println();
        
            if ( choice == 1 ) {
                return Login();
            }

            else if ( choice == 2 ) {
                return CreateAccount();
            }

            else {
                System.out.println("Invalid choice! Try again.");
                continue;
            }
        }// while true
    } //if foundUser == null

        // Repeat until correct
        while ( !foundUser.getPassword().equals(password) ) {
            System.out.println("Wrong password");
            System.out.print("Enter password again: ");
            password = in.nextLine();
        }

        System.out.println("Login successful");
        String fullName = foundUser.getFirstName() + " " + foundUser.getLastName();
        return fullName;
    } // Login
    
    
    
    
    
    
    
    
    public String ChooseShop() {
        Scanner in = new Scanner(System.in);
        System.out.println("**************************************************************");

        while (true) {

            // Print all shops with numbers
            for ( int i = 0; i < shops.size(); i++ ) {
                System.out.println( (i + 1) + ". " + shops.get(i).getName() );
            }
            
            System.out.print("Choose a shop: ");

            // Validate that input is a number
            if ( !in.hasNextInt() ) {
                System.out.println("Invalid input! Enter a number.\n");
                in.next(); // remove invalid input
                continue;
            }

            int choice = in.nextInt();

             // Validate range of choice
            if ( choice < 1 || choice > shops.size() ) {
                System.out.println("Invalid choice! Try again.\n");
                continue;
            }

             // Return selected shop
            Shop selectedShop = shops.get(choice - 1);
            return selectedShop.getName();

        }// while true
    } //chooseShop
    
    
    
    
    public int AddToBasket(String shopName) {
        Scanner input = new Scanner(System.in);
        
        Shop shop = null;

        for ( Shop s : shops ) {
            if ( s.getName().equals(shopName) ) {
                shop = s;
                break;
            }
        }

        int choice = -1;
     
        // Loop until user enters 0
        while (choice != 0) {
            System.out.println();
            shop.printMenu();  // Display menu of selected shop
            System.out.print("Choose item to add to basket (0 to finish): ");

            // Validate input is integer
            if (!input.hasNextInt()) {
                System.out.println("Invalid input! Enter a number.");
                input.next();
                continue;
            }

            choice = input.nextInt();

            if ( choice == 0 )
                break;

            if ( choice < 1 || choice > shop.getItems().size() ) {
                System.out.println("Invalid item! Try again.");
                continue;
            }

            Item selectedItem = shop.getItems().get(choice - 1);

            if ( selectedItem.getAvailableQuantity() == 0 ) {
                System.out.println("Sorry, this item is out of stock.");
                continue;
            }


            System.out.print("Enter quantity: ");

            if ( !input.hasNextInt() ) {
                System.out.println("Invalid input! Enter a number.");
                input.next();
                continue;
            }

            int Quantity = input.nextInt();

             // Validate quantity
            if ( Quantity < 1 ) {
                System.out.print("Invalid quantity! Try again.\n");
                continue;
            }


            if ( Quantity > selectedItem.getAvailableQuantity() ) {
                System.out.println("Sorry, only " + selectedItem.getAvailableQuantity() + " available.");
                continue;
            }


            // If item already exists in basket → increase quantity
            if ( basketItems.contains(selectedItem) ) {
                int index = basketItems.indexOf(selectedItem);
                quantities.set(index, quantities.get(index) + Quantity);

            }

            // Otherwise add new item
            else {
                basketItems.add(selectedItem);
                quantities.add(Quantity);
            }

            selectedItem.reduceQuantity(Quantity);

            int addedTotal = selectedItem.getPrice() * Quantity;
            // Show confirmation message
            System.out.print("Added " + selectedItem.getName() + " x" + Quantity + " = " + addedTotal + " SAR\n");

        }//while

         // Print final basket
        System.out.println("\nYour Basket:");
        int total = 0;

        // Calculate total and print each item
        for ( int i = 0; i < basketItems.size(); i++ ) {
            Item item = basketItems.get(i);
            int quantity = quantities.get(i);

            int subTotal = item.getPrice() * quantity;
            total += subTotal;

            System.out.println(item.getName() + " x" + quantity + " = " + subTotal + " SAR");
        }

        System.out.println("----------------------");

        // If basket is empty
        if ( total == 0 ) {
            System.out.println("Your basket is empty. No items added. Try again.\n");
            shopName = ChooseShop();
            total = AddToBasket(shopName);
        }
         
        else
            System.out.println("Total = " + total + " SAR");
        
        return total;
    }//addToBasket
    
    
    
    
    

    
    
    public boolean processPayment(int amount) {
        Scanner in = new Scanner(System.in);
        boolean paymentState = false;
        
        while ( true ) {
            System.out.println();
            System.out.println("1) Cash");
            System.out.println("2) Card");
            System.out.print("Enter method of payment: ");
            
            if ( !in.hasNextInt() ) {
                System.out.println("Invalid input! Enter a number.\n");
                in.next(); // remove invalid input
                continue;
            }
            
            int choice = in.nextInt();
            in.nextLine();


            // if cash
            if ( choice == 1 ) {
                return false;
            } 

            // if card
            else if ( choice == 2 ) {
                
                String cardNumber;
                while (true) {
                    
                    System.out.print("Enter card number (16 digits): ");
                    cardNumber = in.nextLine();
                    
                    
                    if ( cardNumber.matches("\\d{16}") ) {
                        break;
                    }
                    else {
                        System.out.println("Invalid card number!");
                    }
                }// while for card num
                
                String name;
                while ( true ) {
                    System.out.print("Enter card holder name: ");
                    name = in.nextLine();

                    if ( !name.trim().isEmpty() ) {
                        break;
                    }
                    else {
                        System.out.println("Invalid name!");
                    }
                }// while for name
                
                
                // Expiry Date
                String expiry;
                while ( true ) {
                    System.out.print("Enter expiry date (MM/YY): ");
                    expiry = in.nextLine();

                    if ( expiry.matches("(0[1-9]|1[0-2])/\\d{2}") ) {
                        break;
                    }
                    else {
                        System.out.println("Invalid expiry date!");
                    }
                } // while for expiry day
                
                // CVV
                String cvv;
                while ( true ) {
                    System.out.print("Enter CVV (3 digits): ");
                    cvv = in.nextLine();

                    if ( cvv.matches("\\d{3}") ) {
                        break;
                    }
                    else {
                        System.out.println("Invalid CVV!");
                    }
                }// while fpr cvv
                    
                    
                System.out.println("Payment Successful");
                return true;  
            }

            else {
                System.out.println("Invalid choice! Try again.\n");
                in.nextLine();
            }
            
        } //true
    }
    
    
    
    
    public void printInvoice(String userName, int total, boolean paid) {
        System.out.println("\nYour invoice:");
        System.out.println("********** CampusBite Invoice **********");
        
        System.out.println("User: " + userName);
        
        java.time.LocalDate date = java.time.LocalDate.now();
        System.out.println("Date: " + date);
        System.out.println();
        System.out.println("----------------------");
        
        System.out.println("\nYour Basket:");

        // Calculate total and print each item
        for ( int i = 0; i < basketItems.size(); i++ ) {
            Item item = basketItems.get(i);
            int quantity = quantities.get(i);

            int subTotal = item.getPrice() * quantity;
            System.out.println(item.getName() + " x" + quantity + " = " + subTotal + " SAR");
        }
        System.out.println("----------------------");
        System.out.println("Total = " + total + " SAR");
        System.out.println();
        
        
        if ( paid ) {
            System.out.println("Payment Method: Card");
            System.out.println("Payment Status: Successful");
        }
        else {
            System.out.println("Payment Method: Cash");
            System.out.println("Payment Status: Pending");
        }
        
        System.out.println("********** CampusBite Invoice **********\n");
    }
    
    
    
   
    
    public void writeReview() {
        Scanner in = new Scanner(System.in);
        int rating;
        
        while (true){
            System.out.print("Rate your experience (0 to 5): ");
            
            if ( !in.hasNextInt() ) {
            System.out.println("Invalid input! Enter a number.");
            in.next();
            continue;
        }

            rating = in.nextInt();

            if ( rating >= 0 && rating <= 5 ) {
                break;
            }
            else {
                System.out.println("Rating must be between 0 and 5.");
            }
        }// while for rating
        
        in.nextLine();
        System.out.print("Write a review (or press 0 to skip): ");
        String Comment = in.nextLine();
        
        if ( Comment.equals("0") ) {
            Comment = "No review";
        }
   
        System.out.println("\nThank you for your feedback!");
        System.out.println("Rating: " + rating + " stars");
        System.out.println("Review: " + Comment);
        System.out.println("\nThank you for using CampusBite!\n");
    }

    
}
