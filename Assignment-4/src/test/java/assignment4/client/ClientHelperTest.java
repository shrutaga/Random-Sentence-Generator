package assignment4.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientHelperTest {
    private List<String> titles;
    Scanner scanner;

    @BeforeEach
    void setup() {
        titles = Arrays.asList("Test Grammar 1", "Test Grammar 2", "Test Grammar 3");

        String data = "1\n q";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        scanner = new Scanner(System.in);
    }

    @Test
    void testGetUserSelectionMethod(){
        int option = ClientHelper.getUserSelection(titles, scanner);
        assertEquals(option, 1);
    }


}