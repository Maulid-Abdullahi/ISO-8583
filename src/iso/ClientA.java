package iso;

public class ClientA {
    public static void main (String[] args) {
        MTIClient c = new MTIClient("127.0.0.1", 5000);
        c.run();
    }
}
