import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;
import java.util.TreeMap;

class Account {
    private final String customerName;

    public Account(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }
}

class Cafe {
    private final Map<String, Account> accounts = new TreeMap<>();
    public ArrayList<String> ar = new ArrayList<String>();// waiting area
    public ArrayList<String> bp = new ArrayList<String>();// being prepared area
    public ArrayList<String> tr = new ArrayList<String>();// tray area
    public ArrayList<String> dv = new ArrayList<String>();// orders ready area
    public ArrayList<String> cs = new ArrayList<String>();// Number of clients in the café
    public ArrayList<String> co = new ArrayList<String>();// Number of clients waiting for orders.
    String x = "";

    public void createAccount(String customerName) {
        Account account = new Account(customerName);
        accounts.put(customerName, account);
    }

    // Store the Number of clients in the café when connect to server
    public void AddCustomerNumbersInTheCafe(String customerName) {
        cs.add(customerName);
        System.out.println("Number of customers in the cafe: " + cs.size());
        createAccount(customerName);
    }

    // remove the Number of clients in the café when disconnect to server
    public void RemoveCustomerNumbersInTheCafe(String customerName) {
        cs.remove(customerName);
        System.out.println("Number of customers in the cafe: " + cs.size());
        createAccount(customerName);
    }

    // Store how many customers waiting the order
    public void AddCustomerNumberWaitingOrder(String customerName) {
        co.add(customerName);
        System.out.println("Number of clients waiting for orders: " + co.size());
        createAccount(customerName);
    }

    // remove how many customers waiting the order when customers order delivered if
    // the user has no pending orders
    public void RemoveCustomerNumberWaitingOrder(String customerName) {
        System.out.println("Number of clients waiting for orders: " + co.size());
        createAccount(customerName);
    }

    // Remove the customers order when customer leave.
    public void RemoveCustomerOder(String customerName) {

        System.out.println(customerName + " orders have been deleted: ");
        if (ar.size() == 0 && bp.size() == 0 && tr.size() == 0) {
            System.out.println("No order found for " + customerName);
        }
        if (ar.size() != 0) {
            System.out.println(ar);
        }
        if (bp.size() != 0) {
            System.out.println(bp);
        }
        if (tr.size() != 0) {
            System.out.println(tr);
        }
        ar.clear();
        bp.clear();
        tr.clear();
        dv.clear();
        createAccount(customerName);
    }

    // Taking the customers order
    public String Order(String customerName, String ord, int quan)
            throws InterruptedException, BrokenBarrierException {
        synchronized (customerName) {
            String ordStatus;
            if (quan > 10) {
                ordStatus = "No more than 10 quantity. ";
            } else {
                System.out.println("order received from: " + customerName + " ( " + ord + ": " + quan + " ) ");
                ordStatus = "Server = order received from: " + customerName + " ( " + ord + ": " + quan + " ) ";
                ord = ord + ": " + quan;
                areas(ord);
                AddCustomerNumberWaitingOrder(ord);

            }
            createAccount(customerName);
            return ordStatus;
        }
    }

    // Showing the customers order list
    public String OrderList(String customerName, String ord) {
        if (ar.size() == 0 && bp.size() == 0 && tr.size() == 0 && dv.size() == 0) {
            x = "Server = No order found for: " + customerName;
            System.out.println("Order Status for: " + customerName + " : No order found for");

        } else {
            x = "Order Status for " + customerName + "----->   ";
            x += ar + " in waiting area ";
            x += " --- " + bp + " currently being prepared";
            x += " --- " + tr + " currently in the tray";
            x += " --- " + dv + " delivered to: " + customerName;

            System.out.println("************************************");
            System.out.println("Waiting area: " + ar.size());
            System.out.println("prepared area: " + bp.size());
            System.out.println("tray area: " + tr.size());
            System.out.println(("************************************"));
        }
        if (dv.size() != 0) {
            System.out.println("************************************");
            System.out.println("Waiting area: " + ar.size());
            System.out.println("prepared area: " + bp.size());
            System.out.println("tray area: " + tr.size());
            System.out.println(("************************************"));
            System.out.println(dv + " delivered to: " + customerName);
            RemoveCustomerNumberWaitingOrder(customerName);
        }
        createAccount(customerName);
        return x;
    }

    // Adds the order from the customer to the waiting area first, then to the being
    // prepared area, tray area, orders ready area, respectively and under the
    // necessary conditions.
    public void areas(String ord) throws InterruptedException, BrokenBarrierException {
        synchronized (ord) {
            final CyclicBarrier gate = new CyclicBarrier(5);

            Thread waiting_area = new Thread() {
                public void run() {
                    try {
                        ar.add(ord);
                        gate.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            };

            Thread being_prepared_area = new Thread() {
                public void run() {
                    try {
                        Thread.sleep(5000);
                        ar.remove(ord);
                        bp.add(ord);
                        gate.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            };

            Thread tray_area = new Thread() {
                public void run() {
                    try {
                        Thread.sleep(45000);
                        bp.remove(ord);
                        tr.add(ord);
                        gate.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            };

            Thread tray_area2 = new Thread() {
                public void run() {
                    try {
                        Thread.sleep(30000);
                        bp.remove(ord);
                        tr.add(ord);
                        gate.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            };
            Thread orders_ready_area = new Thread() {
                public void run() {
                    try {
                        Thread.sleep(50000);
                        tr.remove(ord);
                        dv.add(ord);
                        co.remove(ord);
                        gate.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }

            };

            waiting_area.start();
            being_prepared_area.start();
            if (ord.startsWith("coffee") || (ord.startsWith("Coffee"))) {
                tray_area.start();
            } else {
                tray_area2.start();
            }
            orders_ready_area.start();
        }
    }

}

class CustomerHandler implements Runnable {
    public static String customerName;
    public static String ord;
    public static int quan;
    private final Socket socket;
    private Cafe cafe;

    public CustomerHandler(Socket socket, Cafe cafe) {
        this.socket = socket;
        this.cafe = cafe;
    }

    @Override
    public void run() {
        String customerName = "";
        try (
                Scanner scanner = new Scanner(socket.getInputStream());
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            try {
                customerName = (scanner.nextLine());
                System.out.println("New customer; Customer Name: " + customerName);
                cafe.AddCustomerNumbersInTheCafe(customerName);
                writer.println("SUCCESS");

                while (true) {
                    String line = scanner.nextLine();
                    String[] substrings = line.split(" ");
                    switch (substrings[0].toLowerCase()) {
                        case "order":
                            ord = (scanner.nextLine());
                            quan = Integer.parseInt(scanner.nextLine());
                            writer.println(cafe.Order(customerName, ord, quan));
                            break;

                        case "orderl":
                            writer.println(cafe.OrderList(customerName, ord));
                            break;

                        case "exit":
                            writer.println("See you" + customerName + " !!");
                            socket.close();
                            break;

                        default:
                            throw new Exception("Unknown command: " + substrings[0]);
                    }
                }
            } catch (Exception e) {
                writer.println("ERROR " + e.getMessage());
                socket.close();
            }
        } catch (Exception e) {
        } finally {
            System.out.println("Customer " + customerName + " disconnected.");
            cafe.RemoveCustomerNumbersInTheCafe(customerName);
            cafe.RemoveCustomerOder(customerName);
        }
    }
}

public class Barista {
    private final static int port = 6161;

    private static final Cafe cafe = new Cafe();

    public static void main(String[] args) {
        RunServer();
    }

    private static void RunServer() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Waiting for Customer connections...");
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new CustomerHandler(socket, cafe)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
