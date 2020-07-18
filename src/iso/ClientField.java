package iso;

public class ClientField {
    public static void main (String[] args) {
        FieldsClient c = new FieldsClient("127.0.0.1", 6000);
        c.run();
    }
}
