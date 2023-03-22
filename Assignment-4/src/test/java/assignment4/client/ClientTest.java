package assignment4.client;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientTest {
    Scanner scanner;
    private List<String> titles;

   @Test
    void testMainMethod() {
       String data = "1\nn";
       System.setIn(new ByteArrayInputStream(data.getBytes()));
       scanner = new Scanner(System.in);

       String[] args = new String[]{"src/test/resources"};
       Client.main(args);

    }

    @Test
    void testMainMethod1() {
        String data = "1\nq";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        scanner = new Scanner(System.in);

        String[] args = new String[]{"src/test/resources"};
        Client.main(args);

    }

    @Test
    void testMainMethod2() {
        String data = "1\ny\n1\nq";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        scanner = new Scanner(System.in);

        String[] args = new String[]{"src/test/resources"};
        Client.main(args);

    }

    @Test
    void testGetUserSelectionMethod(){
        String data = "1\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        scanner = new Scanner(System.in);

        titles = Arrays.asList("Test Grammar 1", "Test Grammar 2", "Test Grammar 3");
        int option = ClientHelper.getUserSelection(titles, scanner);
        assertEquals(option, 1);
    }

}
