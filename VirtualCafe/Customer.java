import java.util.Scanner;
import java.io.*;
import java.net.Socket;

class CustomerProgram implements AutoCloseable {

    final int port = 6161;
    private final Scanner reader;
    private final PrintWriter writer;

    public CustomerProgram(String customerName) throws Exception {
        Socket socket = new Socket("localhost", port);
        reader = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream(), true);
        writer.println(customerName);
        String line = reader.nextLine();
        if (line.trim().compareToIgnoreCase("success") != 0)
            throw new Exception(line);
    }

    // Taking customer orders and sending to server side
    public void Order(String customerName, String ord, int quan) throws Exception {
        writer.println("ORDER");
        writer.println(ord);
        writer.println(quan);
        String line = reader.nextLine();
        System.out.println(line);
    }

    // Asking to server to show customer list
    public void OrderList(String customerName) throws Exception {
        writer.println("ORDERL");
        String line = reader.nextLine();
        System.out.println(line);
    }

    // EXIT
    public void exit() throws Exception {
        writer.println("EXİT");
    }

    @Override
    public void close() throws Exception {
        reader.close();
        writer.close();
    }

}

public class Customer {
    public static void main(String[] args) {
        System.out.println("Please enter your Name:");
        try {
            Scanner in = new Scanner(System.in);
            String customerName = (in.nextLine());
            try (CustomerProgram client = new CustomerProgram(customerName)) {
                System.out.println("Welcome to Virtual Cafe " + customerName);
                while (true) {
                    System.out.println(
                            "Choose between make a Order (O) or Order Status (S) or Exit (E):");
                    String choice = in.nextLine().trim().toUpperCase();
                    switch (choice) {
                        case "O":
                            System.out.println("Please Write Your Order " + customerName);
                            System.out.print("Order: ");
                            String ord = (in.nextLine());
                            System.out.print("Quantity: ");
                            int quan = Integer.parseInt(in.nextLine());
                            client.Order(customerName, ord, quan);
                            break;

                        case "S":
                            // System.out.println("Order Status for " + customerName + ":");
                            client.OrderList(customerName);
                            break;

                        case "E":
                            client.close();
                            System.exit(0);
                            break;

                        default:
                            System.out.println("Unknown command: " + choice);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}