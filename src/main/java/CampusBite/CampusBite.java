
package CampusBite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CampusBite {
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Functions function = new Functions();
        Read_And_Write_A_File Recall = new Read_And_Write_A_File();
        Recall.loadUsersFromFile();
        Recall.loadShopsFromFile();
        User user;
        
        Scanner in = new Scanner(System.in);
        int choice;
        System.out.println("**************************CampusBite**************************");
        String userName;
        while ( true ) {
            System.out.println("Welcome to CampusBite!");
            System.out.println("1) Login");
            System.out.println("2) Create new account");
            System.out.print("Enter your choice: ");
            
            if ( !in.hasNextInt() ) {
                System.out.println("Invalid input! Enter a number.\n");
                in.next(); // remove invalid input
                continue;
            }
            
            choice = in.nextInt();
            
            if ( choice == 1 ) {
                System.out.println();
                userName = function.Login();
                System.out.println();
                break;
            }

            else if ( choice == 2 ) {
                System.out.println();
                function.CreateAccount();
                userName = function.Login();
                System.out.println();
                break;
            }
            
            
            else {
                System.out.println("Invalid input! Try again.\n");
                in.nextLine();
            }
                      
        } // while true    

        String selectedShop = function.ChooseShop();
        int price = function.AddToBasket(selectedShop);
        boolean paid = function.processPayment(price);
        
        function.printInvoice(userName,price,paid);
        function.writeReview();
        System.out.println("\n**************************CampusBite**************************");
    } //main
}
