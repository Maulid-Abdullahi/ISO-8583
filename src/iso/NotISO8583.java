package iso;

public class NotISO8583 extends Exception {

    public NotISO8583 (String errorMsg) {
        super(errorMsg);
    }
}
