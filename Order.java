interface OrderCalculator {
    double calculateTotal(double price, int quantity);
}

class SimpleOrderCalculator implements OrderCalculator {
    @Override
    public double calculateTotal(double price, int quantity) {
        return price * quantity;
    }
}

interface OrderPlacer {
    void placeOrder(String customerName, String address);
}

class SimpleOrderPlacer implements OrderPlacer {
    @Override
    public void placeOrder(String customerName, String address) {
        System.out.println("Order placed for " + customerName + " at " + address);
    }
}

interface InvoiceGenerator {
    void generateInvoice(String fileName);
}

class FileInvoiceGenerator implements InvoiceGenerator {
    @Override
    public void generateInvoice(String fileName) {
        System.out.println("Invoice generated: " + fileName);
    }
}

interface NotificationSender {
    void sendNotification(String message, String recipient);
}

class EmailNotificationSender implements NotificationSender {
    @Override
    public void sendNotification(String message, String recipient) {
        System.out.println("Email notification sent to: " + recipient + ". Message: " + message);
    }
}

class OrderManager {
    private OrderCalculator calculator;
    private OrderPlacer placer;
    private InvoiceGenerator invoiceGenerator;
    private NotificationSender notificationSender;

    public OrderManager(OrderCalculator calculator, OrderPlacer placer, InvoiceGenerator invoiceGenerator, NotificationSender notificationSender) {
        this.calculator = calculator;
        this.placer = placer;
        this.invoiceGenerator = invoiceGenerator;
        this.notificationSender = notificationSender;
    }

    public void processOrder(double price, int quantity, String customerName, String address, String invoiceFileName, String email) {
        double total = calculator.calculateTotal(price, quantity);
        System.out.println("Order total: $" + total);
        placer.placeOrder(customerName, address);
        if(invoiceGenerator != null) {
            invoiceGenerator.generateInvoice(invoiceFileName);
        }
        if(notificationSender != null) {
            notificationSender.sendNotification("Your order has been processed", email);
        }
    }

    public void processOrder(double price, int quantity, String customerName, String address) {
        double total = calculator.calculateTotal(price, quantity);
        System.out.println("Order total: $" + total);
        placer.placeOrder(customerName, address);
    }
}

public class OrderTest {
    public static void main(String[] args) {
        OrderCalculator calculator = new SimpleOrderCalculator();
        OrderPlacer placer = new SimpleOrderPlacer();
        InvoiceGenerator invoiceGenerator = new FileInvoiceGenerator();
        NotificationSender notificationSender = new EmailNotificationSender();

        OrderManager orderManagerWithAll = new OrderManager(calculator, placer, invoiceGenerator, notificationSender);
        orderManagerWithAll.processOrder(10.0, 2, "John Doe", "123 Main St", "order_123.pdf", "johndoe@example.com");

        OrderManager orderManagerWithoutExtras = new OrderManager(calculator, placer, null, null);
        orderManagerWithoutExtras.processOrder(10.0, 2, "Jane Doe", "456 Side St");

    }
}
