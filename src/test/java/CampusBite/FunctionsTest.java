
package CampusBite;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;



public class FunctionsTest {
    Functions function;
    Read_And_Write_A_File file;
    String originalUsersFile;
    
    @Before
    public void setUp() throws Exception {
        originalUsersFile = new String(Files.readAllBytes(Paths.get("users.txt")));
        
        Read_And_Write_A_File.users.clear();
        Read_And_Write_A_File.shops.clear();
        Functions.basketItems.clear();
        Functions.quantities.clear();

        file = new Read_And_Write_A_File();
        file.loadUsersFromFile();
        file.loadShopsFromFile();

        function = new Functions();
    }
    

    @Test
    public void testCreateAccount() throws Exception {
        String input =
                "Hebah\n" +
                "Albatati\n" +
                "Halbatati@kau.edu.sa\n" +
                "hebah123\n" +
                "0591111111\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        String result = function.CreateAccount();

        assertEquals("Hebah Albatati", result);
    }

    
    
    
    @Test
    public void testLogin() throws Exception {
        String input =
                "0551234567\nSeham123\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        String result = function.Login();

        assertEquals("Seham Alghamdi", result);
    }

    
    
    
    
    @Test
    public void testAddToBasket() {
        String input =
                "1\n2\n0\n"; 

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        int total = function.AddToBasket("Barn's");

        assertEquals(18, total);
    }

    
    
    
    
    @Test
    public void testProcessPayment() {
        String input = "1\n"; // Cash

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        boolean result = function.processPayment(18);

        assertFalse(result);
    }

    
    
    @Test
    public void testWriteReview() {
        String input =
                "5\n" +
                "Great application\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        function.writeReview();

        String result = output.toString();

        assertTrue(result.contains("Rating: 5 stars"));
        assertTrue(result.contains("Review: Great application"));
    }
    
    
    
    @After
    public void tearDown() throws Exception {
        Files.write(Paths.get("users.txt"), originalUsersFile.getBytes());
    }
    
     
}
